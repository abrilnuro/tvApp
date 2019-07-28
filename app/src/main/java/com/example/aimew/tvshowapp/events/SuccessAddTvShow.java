package com.example.aimew.tvshowapp.events;

import com.example.aimew.tvshowapp.models.TvShow;

public class SuccessAddTvShow {

    TvShow show;

    public SuccessAddTvShow(TvShow show) {
        this.show = show;
    }

    public TvShow getShow() {
        return show;
    }

    public void setShow(TvShow show) {
        this.show = show;
    }
}
