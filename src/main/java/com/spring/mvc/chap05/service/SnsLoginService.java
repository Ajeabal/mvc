package com.spring.mvc.chap05.service;

import com.spring.mvc.chap05.dto.request.SignUpRequestDTO;
import com.spring.mvc.chap05.dto.response.KakaoUserResponseDTO;
import com.spring.mvc.chap05.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.spring.mvc.chap05.entity.Member.LoginMethod.KAKAO;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnsLoginService {
    private final MemberService memberService;

    // 카카오 로그인 처리
    public void kakaoLogin(Map<String, String> requestParam, HttpSession session) {
        // 인가 코드를 가지고 토큰을 발급 받는 요청 보내기
        String kakaoAccessToken = getKakaoAccessToken(requestParam);
        log.debug("kakaoAccessToken: {}", kakaoAccessToken);

        // 토큰을 통해서 사용자 정보 가져오기
        KakaoUserResponseDTO dto = getKakaoUserInfo(kakaoAccessToken);
        // 카카오에서 받은 회원정보로 우리 사이트 회원가입
        String nickname = dto.getProperties().getNickname();

        // 회원 중복확인 (email이 없어서 중복확인을 닉네임으로 해둠 원래는 email로 해야함)
        if (!memberService.checkDuplicateValue("account", nickname)) {

            memberService.join(
                    SignUpRequestDTO.builder()
                            .account(nickname)
                            .password("0000")
                            .name(nickname)
                            .email(nickname + "@abc.com")
                            .loginMethod(KAKAO)
                            .build(),
                    dto.getProperties().getProfileImage()
            );
        }

        // 우리 사이트 로그인 처리
        memberService.maintainLoginState(session, nickname);

    }

    private KakaoUserResponseDTO getKakaoUserInfo(String kakaoAccessToken) {
        String requestUri = "https://kapi.kakao.com/v2/user/me\t";
        // 요청 헤더
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + kakaoAccessToken);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RestTemplate template = new RestTemplate();
        ResponseEntity<KakaoUserResponseDTO> responseEntity = template.exchange(
                requestUri,
                HttpMethod.POST,
                new HttpEntity<>(httpHeaders),
                KakaoUserResponseDTO.class
        );

        // 응답정보 꺼내기
        KakaoUserResponseDTO responseJSON = responseEntity.getBody();
        log.debug("userPofile: {}", responseJSON);

        return responseJSON;
    }

    // 토큰 발급 요청
    private String getKakaoAccessToken(Map<String, String> requestParam) {
        // 요청 URI
        String requestUri = "https://kauth.kakao.com/oauth/token";
        // 요청 헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // 요청 바디에 파라미터 설정
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", requestParam.get("appkey"));
        params.add("redirect_uri", requestParam.get("redirect"));
        params.add("code", requestParam.get("code"));
        // 카카오 인증서버로 POST 요청
        RestTemplate template = new RestTemplate();
        HttpEntity<Object> requestEntity = new HttpEntity<>(params, httpHeaders);

        /*
            - RestTemplate객체가 REST API 통신을 위한 API (JS의 fetch)
            - 서버에 통신을 보내면서 응답을 받을 수 있는 메서드가 exchange

            param1: 요청 URL
            param2: 요청 방식 (get, post, put, patch, delete...)
            param3: 요청 헤더와 요청 바디 정보 - HttpEntity로 포장해서 줘야 한다.
            param4: 응답결과를 어떤 타입으로 받아낼 것인지 (ex: DTO로 받을건지 Map으로 받을건지)
         */
        ResponseEntity<Map> responseEntity = template.exchange(requestUri, HttpMethod.POST, requestEntity, Map.class);

        // 응답 데이터에서 JSON 추출
        Map<String, Object> responseJSON = (Map<String, Object>) responseEntity.getBody();
        log.debug("응답 데이터:{}", responseJSON);

        // access token 추출
        String accessToken = (String) responseJSON.get("access_token");

        return accessToken;
    }
}
