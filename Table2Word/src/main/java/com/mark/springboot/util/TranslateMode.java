package com.mark.springboot.util;

import java.util.List;

/**
 * @author ：Lxin
 * @date ：Created in 2020/10/12 14:41
 */
public class TranslateMode {
    String from,to;
    List data;
    public class Data{
        String dst,src;

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public List getData() {
        return data;
    }
    public void setData(List data) {
        this.data = data;
    }
}
