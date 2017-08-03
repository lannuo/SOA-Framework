package com.jun.soa.resolver;

import com.jun.soa.page.dubbo.DubboPageRequest;
import com.jun.soa.utils.SpringDataAnnotationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Method;

public class DubboPageableHandlerMethodArgumentResolver implements PageableArgumentResolver {
    private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();
    private static final String INVALID_DEFAULT_PAGE_SIZE = "Invalid default page size configured for method %s! Must not be less than one!";
    private static final String DEFAULT_PAGE_PARAMETER = "page";
    private static final String DEFAULT_SIZE_PARAMETER = "size";
    private static final String DEFAULT_PREFIX = "";
    private static final String DEFAULT_QUALIFIER_DELIMITER = "_";
    private static final int DEFAULT_MAX_PAGE_SIZE = 2000;
    static final Pageable DEFAULT_PAGE_REQUEST = new DubboPageRequest(0, 20);
    private Pageable fallbackPageable;
    private SortArgumentResolver sortResolver;
    private String pageParameterName;
    private String sizeParameterName;
    private String prefix;
    private String qualifierDelimiter;
    private int maxPageSize;
    private boolean oneIndexedParameters;

    public DubboPageableHandlerMethodArgumentResolver() {
        this((SortArgumentResolver)null);
    }

    public DubboPageableHandlerMethodArgumentResolver(SortHandlerMethodArgumentResolver sortResolver) {
        this((SortArgumentResolver)sortResolver);
    }

    public DubboPageableHandlerMethodArgumentResolver(SortArgumentResolver sortResolver) {
        this.fallbackPageable = DEFAULT_PAGE_REQUEST;
        this.pageParameterName = "page";
        this.sizeParameterName = "size";
        this.prefix = "";
        this.qualifierDelimiter = "_";
        this.maxPageSize = 2000;
        this.oneIndexedParameters = false;
        this.sortResolver = (SortArgumentResolver)(sortResolver == null ? DEFAULT_SORT_RESOLVER : sortResolver);
    }

    public void setFallbackPageable(Pageable fallbackPageable) {
        this.fallbackPageable = fallbackPageable;
    }

    public boolean isFallbackPageable(Pageable pageable) {
        return this.fallbackPageable == null ? false : this.fallbackPageable.equals(pageable);
    }

    public void setMaxPageSize(int maxPageSize) {
        this.maxPageSize = maxPageSize;
    }

    protected int getMaxPageSize() {
        return this.maxPageSize;
    }

    public void setPageParameterName(String pageParameterName) {
        Assert.hasText(pageParameterName, "Page parameter name must not be null or empty!");
        this.pageParameterName = pageParameterName;
    }

    protected String getPageParameterName() {
        return this.pageParameterName;
    }

    public void setSizeParameterName(String sizeParameterName) {
        Assert.hasText(sizeParameterName, "Size parameter name must not be null or empty!");
        this.sizeParameterName = sizeParameterName;
    }

    protected String getSizeParameterName() {
        return this.sizeParameterName;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix == null ? "" : prefix;
    }

    public void setQualifierDelimiter(String qualifierDelimiter) {
        this.qualifierDelimiter = qualifierDelimiter == null ? "_" : qualifierDelimiter;
    }

    public void setOneIndexedParameters(boolean oneIndexedParameters) {
        this.oneIndexedParameters = oneIndexedParameters;
    }

    protected boolean isOneIndexedParameters() {
        return this.oneIndexedParameters;
    }

    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.equals(parameter.getParameterType());
    }

    public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        SpringDataAnnotationUtils.assertPageableUniqueness(methodParameter);
        Pageable defaultOrFallback = this.getDefaultFromAnnotationOrFallback(methodParameter);
        String pageString = webRequest.getParameter(this.getParameterNameToUse(this.pageParameterName, methodParameter));
        String pageSizeString = webRequest.getParameter(this.getParameterNameToUse(this.sizeParameterName, methodParameter));
        boolean pageAndSizeGiven = StringUtils.hasText(pageString) && StringUtils.hasText(pageSizeString);
        if (!pageAndSizeGiven && defaultOrFallback == null) {
            return null;
        } else {
            int page = StringUtils.hasText(pageString) ? this.parseAndApplyBoundaries(pageString, 2147483647, true) : defaultOrFallback.getPageNumber();
            int pageSize = StringUtils.hasText(pageSizeString) ? this.parseAndApplyBoundaries(pageSizeString, this.maxPageSize, false) : defaultOrFallback.getPageSize();
            pageSize = pageSize < 1 ? defaultOrFallback.getPageSize() : pageSize;
            pageSize = pageSize > this.maxPageSize ? this.maxPageSize : pageSize;
            Sort sort = this.sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
            sort = sort == null && defaultOrFallback != null ? defaultOrFallback.getSort() : sort;
            return new DubboPageRequest(page, pageSize, sort);
        }
    }

    protected String getParameterNameToUse(String source, MethodParameter parameter) {
        StringBuilder builder = new StringBuilder(this.prefix);
        if (parameter != null && parameter.hasParameterAnnotation(Qualifier.class)) {
            builder.append(((Qualifier)parameter.getParameterAnnotation(Qualifier.class)).value());
            builder.append(this.qualifierDelimiter);
        }

        return builder.append(source).toString();
    }

    private Pageable getDefaultFromAnnotationOrFallback(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(PageableDefault.class) ? getDefaultPageRequestFrom(methodParameter) : this.fallbackPageable;
    }

    private static Pageable getDefaultPageRequestFrom(MethodParameter parameter) {
        PageableDefault defaults = (PageableDefault)parameter.getParameterAnnotation(PageableDefault.class);
        Integer defaultPageNumber = defaults.page();
        Integer defaultPageSize = (Integer)SpringDataAnnotationUtils.getSpecificPropertyOrDefaultFromValue(defaults, "size");
        if (defaultPageSize.intValue() < 1) {
            Method annotatedMethod = parameter.getMethod();
            throw new IllegalStateException(String.format("Invalid default page size configured for method %s! Must not be less than one!", annotatedMethod));
        } else {
            return defaults.sort().length == 0 ? new DubboPageRequest(defaultPageNumber.intValue(), defaultPageSize.intValue()) : new DubboPageRequest(defaultPageNumber.intValue(), defaultPageSize.intValue(), defaults.direction(), defaults.sort());
        }
    }

    private int parseAndApplyBoundaries(String parameter, int upper, boolean shiftIndex) {
        try {
            int parsed = Integer.parseInt(parameter) - (this.oneIndexedParameters && shiftIndex ? 1 : 0);
            return parsed < 0 ? 0 : (parsed > upper ? upper : parsed);
        } catch (NumberFormatException var5) {
            return 0;
        }
    }

}
