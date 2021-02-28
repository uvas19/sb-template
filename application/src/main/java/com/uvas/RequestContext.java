package com.uvas;

import javax.annotation.ManagedBean;

import org.springframework.web.context.annotation.RequestScope;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ManagedBean
@RequestScope
@Getter
@Setter
@NoArgsConstructor
public class RequestContext {
    private String requestBody;
}