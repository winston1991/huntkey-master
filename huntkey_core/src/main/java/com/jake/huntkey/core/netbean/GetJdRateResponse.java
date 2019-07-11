/**
 * Copyright 2019 bejson.com
 */
package com.jake.huntkey.core.netbean;

import java.util.List;

/**
 * Auto-generated: 2019-07-10 10:57:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class GetJdRateResponse extends BaseResponse {

    private List<Content> Content;


    public void setContent(List<Content> Content) {
        this.Content = Content;
    }

    public List<Content> getContent() {
        return Content;
    }


    public static class Jd7DayRate {

        private List<String> X;
        private List<String> Y;

        public void setX(List<String> X) {
            this.X = X;
        }

        public List<String> getX() {
            return X;
        }

        public void setY(List<String> Y) {
            this.Y = Y;
        }

        public List<String> getY() {
            return Y;
        }

    }


    public static class RateTop5 {

        private List<String> X;
        private List<String> Y;

        public void setX(List<String> X) {
            this.X = X;
        }

        public List<String> getX() {
            return X;
        }

        public void setY(List<String> Y) {
            this.Y = Y;
        }

        public List<String> getY() {
            return Y;
        }

    }

    public static class StopLine {

        private List<String> X;
        private List<String> Y;

        public void setX(List<String> X) {
            this.X = X;
        }

        public List<String> getX() {
            return X;
        }

        public void setY(List<String> Y) {
            this.Y = Y;
        }

        public List<String> getY() {
            return Y;
        }
    }


    public static class Content {

        private String Rate;
        private Jd7DayRate Jd7DayRate;
        private RateTop5 RateTop5;
        private StopLine StopLine;

        public void setRate(String Rate) {
            this.Rate = Rate;
        }

        public String getRate() {
            return Rate;
        }

        public void setJd7DayRate(Jd7DayRate Jd7DayRate) {
            this.Jd7DayRate = Jd7DayRate;
        }

        public Jd7DayRate getJd7DayRate() {
            return Jd7DayRate;
        }

        public void setRateTop5(RateTop5 RateTop5) {
            this.RateTop5 = RateTop5;
        }

        public RateTop5 getRateTop5() {
            return RateTop5;
        }

        public void setStopLine(StopLine StopLine) {
            this.StopLine = StopLine;
        }

        public StopLine getStopLine() {
            return StopLine;
        }

    }
}