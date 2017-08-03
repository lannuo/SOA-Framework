package com.jun.soa.page.dubbo;


import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class DubboPageImpl<T> extends DubboChunk<T> implements Page<T> {
    private static final long serialVersionUID = 867755909294344406L;
    private long total;
    private Pageable pageable;

    public DubboPageImpl(){

    }
    public DubboPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable);
        this.pageable = pageable;
        this.total = !content.isEmpty() && pageable != null && (long)(pageable.getOffset() + pageable.getPageSize()) > total ? (long)(pageable.getOffset() + content.size()) : total;
    }

    public DubboPageImpl(List<T> content) {
        this(content, (Pageable)null, null == content ? 0L : (long)content.size());
    }

    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int)Math.ceil((double)this.total / (double)this.getSize());
    }

    public long getTotalElements() {
        return this.total;
    }

    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }

    public boolean isLast() {
        return !this.hasNext();
    }

    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return new DubboPageImpl(this.getConvertedContent(converter), this.pageable, this.total);
    }

    public String toString() {
        String contentType = "UNKNOWN";
        List<T> content = this.getContent();
        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", this.getNumber() + 1, this.getTotalPages(), contentType);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof DubboPageImpl)) {
            return false;
        } else {
            DubboPageImpl<?> that = (DubboPageImpl)obj;
            return this.total == that.total && super.equals(obj);
        }
    }

    public int hashCode() {
        int result = 17;
        result = result + 31 * (int)(this.total ^ this.total >>> 32);
        result += 31 * super.hashCode();
        return result;
    }
}
