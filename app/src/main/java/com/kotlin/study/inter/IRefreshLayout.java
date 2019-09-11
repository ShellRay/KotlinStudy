package com.kotlin.study.inter;

public interface IRefreshLayout {
    int REFRESH_SUCCESS = 0x1;
    int REFRESH_FAILURE = 0x1 << 1;
    int REFRESH_NOT_NETWORK = 0x1 << 2;
    int REFRESH_TIMEOUT = 0x1 << 3;

    void setRefreshState(int state);
}
