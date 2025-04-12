package com.common.auth.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("jwt")
@Getter
@Setter
@Component
public class JwtPorps {
    private String accessSecret;

    private String refreshSecret;
}
