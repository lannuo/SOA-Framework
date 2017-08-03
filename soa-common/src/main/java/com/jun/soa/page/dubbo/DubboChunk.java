package com.jun.soa.page.dubbo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class DubboChunk<T> implements Slice<T>, Serializable {
    private static final long serialVersionUID = 867755909294344406L;
    private final List<T> content = new ArrayList();
    private Pageable pageable;

    public DubboChunk(){

    }
    public DubboChunk(List<T> content, Pageable pageable) {
        Assert.notNull(content, "Content must not be null!");
        this.content.addAll(content);
        this.pageable = pageable;
    }

    public int getNumber() {
        return this.pageable == null ? 0 : this.pageable.getPageNumber();
    }

    public int getSize() {
        return this.pageable == null ? 0 : this.pageable.getPageSize();
    }

    public int getNumberOfElements() {
        return this.content.size();
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public boolean isFirst() {
        return !this.hasPrevious();
    }

    public boolean isLast() {
        return !this.hasNext();
    }

    public Pageable nextPageable() {
        return this.hasNext() ? this.pageable.next() : null;
    }

    public Pageable previousPageable() {
        return this.hasPrevious() ? this.pageable.previousOrFirst() : null;
    }

    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    public Sort getSort() {
        return this.pageable == null ? null : this.pageable.getSort();
    }

    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    protected <S> List<S> getConvertedContent(Converter<? super T, ? extends S> converter) {
        Assert.notNull(converter, "Converter must not be null!");
        List<S> result = new ArrayList(this.content.size());
        Iterator var3 = this.iterator();

        while(var3.hasNext()) {
            T element = (T)var3.next();
            result.add(converter.convert(element));
        }

        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof DubboChunk)) {
            return false;
        } else {
            DubboChunk<?> that = (DubboChunk)obj;
            boolean contentEqual = this.content.equals(that.content);
            boolean pageableEqual = this.pageable == null ? that.pageable == null : this.pageable.equals(that.pageable);
            return contentEqual && pageableEqual;
        }
    }

    public int hashCode() {
        int result = 17;
        result = result + 31 * (this.pageable == null ? 0 : this.pageable.hashCode());
        result += 31 * this.content.hashCode();
        return result;
    }
}
