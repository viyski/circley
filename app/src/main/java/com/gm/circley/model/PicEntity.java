package com.gm.circley.model;

/**
 * Created by lgm on 2016/7/24.
 */
public class PicEntity {

    /**
     * pic : http://r3.sinaimg.cn/10230/2016/0724/68/4/79613754/auto.jpg
     * alt : 7月23日，井陉县南峪镇台头村，村民拉着车子走向村外，车上载着在洪水中遇难亲人的遗体。记者从河北省井陉县政府了解到，截至7月23日11时，暴雨洪涝灾害已造成26人死亡、34人失联。摄影：赵迪/中国青年报
     * kpic : http://l.sinaimg.cn/www/dy/slidenews/1_img/2016_29/2841_715286_908829.jpg/original.jpg
     */

    private String pic;
    private String alt;
    private String kpic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getKpic() {
        return kpic;
    }

    public void setKpic(String kpic) {
        this.kpic = kpic;
    }
}
