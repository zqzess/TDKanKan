package com.tdkankan.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdkankan.R;
/**
 * @author ZQZESS
 * @date 12/9/2020-12:56 AM
 * @file BookCityTopFragment.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookCityTypeFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bookcity_typefragment, container, false);
        return view;
    }
}
