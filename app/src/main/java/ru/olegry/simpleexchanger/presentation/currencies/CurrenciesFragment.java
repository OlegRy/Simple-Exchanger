package ru.olegry.simpleexchanger.presentation.currencies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import ru.olegry.simpleexchanger.App;
import ru.olegry.simpleexchanger.R;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.presentation.base.BaseFragment;
import ru.olegry.simpleexchanger.presentation.currencies.adapter.CurrencyAdapter;

public class CurrenciesFragment extends BaseFragment implements CurrenciesView, CurrencyAdapter.OnItemClickListener {

    private static final String TAG = CurrenciesFragment.class.getSimpleName();
    private static final String BUNDLE_IS_INITIAL = TAG + ".BUNDLE_IS_INITIAL";

    private ViewHolder mViewHolder;
    private CurrenciesPresenter mPresenter;
    private CurrencyAdapter mAdapter;
    private boolean mInitial;

    public CurrenciesFragment() {
        mPresenter = App.get(CurrenciesPresenter.class);
    }

    public static CurrenciesFragment newInstance(boolean isInitial) {
        CurrenciesFragment fragment = new CurrenciesFragment();
        Bundle args = new Bundle();
        args.putBoolean(BUNDLE_IS_INITIAL, isInitial);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArgs(savedInstanceState != null ? savedInstanceState : getArguments());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    protected void destroyPresenter() {
        mPresenter.onDestroy();
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_currencies;
    }

    @Override
    public void showProgress() {
        mViewHolder.mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mViewHolder.mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showCurrencies(List<Currency> currencies) {
        if (mAdapter != null) mAdapter.update(currencies);
    }

    @Override
    public void showError(Throwable error) {
        error.printStackTrace();
        Toast.makeText(getContext(), R.string.error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Currency chosenCurrency) {
        mPresenter.onCurrencySelected(chosenCurrency, mInitial);
    }

    @Override
    public void goBack() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
    }

    private void initArgs(Bundle bundle) {
        if (bundle != null) mInitial = bundle.getBoolean(BUNDLE_IS_INITIAL, false);
    }

    private void initViews(View view) {
        mViewHolder = new ViewHolder(view);
        initToolbar();
        initRecyclerView();
    }

    private void initToolbar() {
        Context context = getContext();
        mViewHolder.mToolbar.setTitle(mInitial ? R.string.initial_currency : R.string.result_currency);
        if (context != null) {
            mViewHolder.mToolbar.setNavigationIcon(ContextCompat.getDrawable(context, R.drawable.ic_arrow_back));
            mViewHolder.mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() != null) getActivity().onBackPressed();
                }
            });
        }
    }

    private void initRecyclerView() {
        mAdapter = new CurrencyAdapter();
        mAdapter.setListener(this);
        mViewHolder.mCurrencies.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewHolder.mCurrencies.setAdapter(mAdapter);
    }

    private class ViewHolder {
        private Toolbar mToolbar;
        private RecyclerView mCurrencies;
        private View mProgress;

        ViewHolder(View view) {
            mToolbar = view.findViewById(R.id.toolbar);
            mCurrencies = view.findViewById(R.id.rv_currencies);
            mProgress = view.findViewById(R.id.progress);
        }
    }
}
