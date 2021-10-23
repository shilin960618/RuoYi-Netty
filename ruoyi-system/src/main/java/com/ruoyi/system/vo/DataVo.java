package com.ruoyi.system.vo;

public class DataVo {
    // a项电压
    private String av;
    // b项电压
    private String bv;
    // c项电压
    private String cv;
    // a项电流
    private String ai;
    // b项电流
    private String bi;
    // c项电流
    private String ci;
    // 有功功率
    private String aw;
    // 无功功率
    private String bw;
    // 功率因数
    private String cw;

    public String getAv() {
        return av;
    }

    public void setAv(String av) {
        this.av = av;
    }

    public String getBv() {
        return bv;
    }

    public void setBv(String bv) {
        this.bv = bv;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getAi() {
        return ai;
    }

    public void setAi(String ai) {
        this.ai = ai;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getAw() {
        return aw;
    }

    public void setAw(String aw) {
        this.aw = aw;
    }

    public String getBw() {
        return bw;
    }

    public void setBw(String bw) {
        this.bw = bw;
    }

    public String getCw() {
        return cw;
    }

    public void setCw(String cw) {
        this.cw = cw;
    }

    @Override
    public String toString() {
        return "DataVo{" +
                "av='" + av + '\'' +
                ", bv='" + bv + '\'' +
                ", cv='" + cv + '\'' +
                ", ai='" + ai + '\'' +
                ", bi='" + bi + '\'' +
                ", ci='" + ci + '\'' +
                ", aw='" + aw + '\'' +
                ", bw='" + bw + '\'' +
                ", cw='" + cw + '\'' +
                '}';
    }
}
