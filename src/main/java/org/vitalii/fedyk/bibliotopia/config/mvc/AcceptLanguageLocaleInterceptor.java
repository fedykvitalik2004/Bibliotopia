package org.vitalii.fedyk.bibliotopia.config.mvc;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;
import java.util.Objects;

@Component
@Slf4j
public class AcceptLanguageLocaleInterceptor implements HandlerInterceptor {
    @Resource(name = "localeHolder")
    private LocaleHolder localeHolder;

    @Override
    public boolean preHandle(@NonNull final HttpServletRequest request,
                             @NonNull final HttpServletResponse response,
                             @NonNull final Object handler) {
        log.debug("Accept-Language: {}", request.getHeader(HttpHeaders.ACCEPT_LANGUAGE));
        final LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (Objects.isNull(localeResolver)) {
            log.warn("LocaleResolver is null");
        } else if (localeResolver instanceof AcceptHeaderLocaleResolver acceptHeaderLocaleResolver) {
            final Locale resolvedLocale = acceptHeaderLocaleResolver.resolveLocale(request);
            log.debug("Resolved Locale: {}", resolvedLocale);
            localeHolder.setLocale(resolvedLocale);
        } else {
            log.warn("Using not supported LocaleResolver: {}", localeResolver);
        }
        return true;
    }
}
