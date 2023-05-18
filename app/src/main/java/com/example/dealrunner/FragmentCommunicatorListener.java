package com.example.dealrunner;

import android.os.Bundle;

public interface FragmentCommunicatorListener {
    void loginButtonClicked();
    void signupButtonClicked();
    void continueButtonClicked(Bundle bundle);
    void grabDealsButtonClicked();
    void postDealsButtonClicked();
    void pushDealButtonClicked();
}
