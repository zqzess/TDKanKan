package com.tdkankan.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdkankan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZQZESS
 * @date 12/8/2020-9:41 PM
 * @file BookCityFragment.java
 * GitHub：https://github.com/zqzess
 *不会停止运行的app不是好app w(ﾟДﾟ)w
 */
public class BookCityFragment extends Fragment {

    private ViewPager viewPager;// 声明一个viewpager对象
    private TextView tv1;
    private TextView tv2;
    private TextView tv_search;
    private ImageView tabline;
    private List<Fragment> list;// 声明一个list集合存放Fragment（数据源）

    private int tabLineLength;// 1/2屏幕宽
    private int currentPage = 0;// 初始化当前页为0（第一页）
    LinearLayout.LayoutParams ll;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.mywx);
//        // 初始化滑动条1/3
//        initTabLine();
//
//        // 初始化界面
//        initView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_bookcity, container, false);
        // 获取控件实例
        tabline = (ImageView) view.findViewById(R.id.tabline);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        ll = (android.widget.LinearLayout.LayoutParams) tabline.getLayoutParams();
        tv_search=(TextView)view.findViewById(R.id.tv_search);


        // 初始化滑动条1/2
        initTabLine();
        // 初始化界面
        initView();

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.leftMargin = (int) (0* tabLineLength + 0 * tabLineLength);
                        tabline.setLayoutParams(ll);
                tv1.setTextColor(Color.rgb(51, 153, 0));
                tv2.setTextColor(Color.BLACK);
                viewPager.setCurrentItem(0);

            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.leftMargin = (int) (1* tabLineLength + 0 * tabLineLength);
                tabline.setLayoutParams(ll);
                tv2.setTextColor(Color.rgb(51, 153, 0));
                tv1.setTextColor(Color.BLACK);
                viewPager.setCurrentItem(1);
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BookCityFragment.this.getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initTabLine() {
        // 获取显示屏信息
        Display display = this.getActivity().getWindowManager().getDefaultDisplay();
        // 得到显示屏宽度
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 1/2屏幕宽度
        tabLineLength = metrics.widthPixels / 2;

        // 控件参数
        ViewGroup.LayoutParams lp = tabline.getLayoutParams();
        lp.width = tabLineLength;
        tabline.setLayoutParams(lp);
    }

    private void initView() {
        // 实例化对象

        list = new ArrayList<Fragment>();

        // 设置数据源
        BookCityTopFragment fragment1 = new BookCityTopFragment();
        BookCityTypeFragment fragment2 = new BookCityTypeFragment();

        list.add(fragment1);
        list.add(fragment2);

        // 设置适配器
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(
                this.getChildFragmentManager()) {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return list.get(arg0);
            }
        };

        // 绑定适配器
        viewPager.setAdapter(adapter);
        int offset=(tabLineLength-180)/2;//滚动条初始偏移量
        int one=offset*2+tabLineLength;//计算出切换一个界面时，滚动条位移量
        Matrix matrix=new Matrix();
        matrix.postTranslate(offset,0);

        // 设置滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int arg0, float arg1, int positionOffsetPixels) {
                Log.i("tuzi", arg0 + "," + arg1 + "," + positionOffsetPixels);

                // 取得该控件的实例
                LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline
                        .getLayoutParams();

//                if (currentPage == 0) { // 0->1移动(第一页到第二页)
//                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1 * tabLineLength);
//                    ll.leftMargin = (int) (0* tabLineLength + 0 * tabLineLength);
//                }
//                else if (currentPage == 1 && arg0 == 1) { // 1->2移动（第二页到第三页）
//                    ll.leftMargin = (int) (currentPage * tabLineLength + arg1
//                            * tabLineLength);}
//                else if (currentPage == 1) { // 1->0移动（第二页到第一页）
//                    ll.leftMargin = (int) (currentPage * tabLineLength - ((1 - arg1) * tabLineLength));
//                    ll.leftMargin = (int) (1* tabLineLength + 0 * tabLineLength);
//                }

//                else if (currentPage == 2 && arg0 == 1) { // 2->1移动（第三页到第二页）
//                    ll.leftMargin = (int) (currentPage * tabLineLength - (1 - arg1)
//                            * tabLineLength);
//                }

//                tabline.setLayoutParams(ll);

            }

            @Override
            public void onPageSelected(int position) {
                tv1.setTextColor(Color.BLACK);
                tv2.setTextColor(Color.BLACK);
//                LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline.getLayoutParams();

                // 再改变当前选择页（position）对应的textview颜色
                switch (position) {
                    case 0:
                        tv1.setTextColor(Color.rgb(51, 153, 0));
                        ll.leftMargin = (int) (0* tabLineLength + 0 * tabLineLength);
//                        tabline.setLayoutParams(ll);
                        break;
                    case 1:
                        tv2.setTextColor(Color.rgb(51, 153, 0));
                        ll.leftMargin = (int) (1* tabLineLength + 0 * tabLineLength);
//                        tabline.setLayoutParams(ll);
                        break;
                }

                currentPage = position;
                tabline.setLayoutParams(ll);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
    }
}

