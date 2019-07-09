package com.example.android.popularmoviesstate1.features.main;

interface MainNavigator {
    void hideProgressBar();
    void showErrorMessage();
    void showProgressBar();
    void updateMenuItemChecked(int menuItemId);
}