package ru.olegry.simpleexchanger.presentation.exchanger;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ru.olegry.simpleexchanger.App;
import ru.olegry.simpleexchanger.R;
import ru.olegry.simpleexchanger.models.Currency;
import ru.olegry.simpleexchanger.presentation.base.BaseFragment;
import ru.olegry.simpleexchanger.presentation.currencies.CurrenciesFragment;

public class ExchangerFragment extends BaseFragment implements ExchangerView {

    private ExchangerPresenter mPresenter;
    private ViewHolder mViewHolder;

    public ExchangerFragment() {
        mPresenter = App.get(ExchangerPresenter.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewHolder = new ViewHolder(view);
        mPresenter.attachView(this);
        mPresenter.currencies();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showError(Throwable error) {
        error.printStackTrace();
    }

    @Override
    public void showConverterSettings(Currency currency) {
        mViewHolder.mContent.setVisibility(View.VISIBLE);
        mViewHolder.mInitialCurrencyValue.setText(currency.getCharCode());
        mViewHolder.mResultCurrencyValue.setText(currency.getCharCode());
    }

    @Override
    public void showResult(String result) {
        Toast.makeText(getContext(), getString(R.string.result_template, result), Toast.LENGTH_LONG).show();
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
    public void showNewInitialCurrency(Currency currency) {
        mViewHolder.mInitialCurrencyValue.setText(currency.getCharCode());
    }

    @Override
    public void showNewResultCurrency(Currency currency) {
        mViewHolder.mResultCurrencyValue.setText(currency.getCharCode());
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_exchanger;
    }

    private class ViewHolder {

        private TextInputEditText mAmount;
        private View mInitialCurrencyContainer;
        private TextView mInitialCurrencyValue;
        private View mResultCurrencyContainer;
        private TextView mResultCurrencyValue;
        private View mProgress;
        private View mContent;
        private View mConvert;

        ViewHolder(View view) {
            mAmount = view.findViewById(R.id.tiet_amount);
            mInitialCurrencyContainer = view.findViewById(R.id.ll_initial_currency);
            mInitialCurrencyValue = view.findViewById(R.id.tv_initial_currency);
            mResultCurrencyContainer = view.findViewById(R.id.ll_result_currency);
            mResultCurrencyValue = view.findViewById(R.id.tv_result_currency);
            mProgress = view.findViewById(R.id.progress);
            mContent = view.findViewById(R.id.ll_content);
            mConvert = view.findViewById(R.id.btn_convert);

            mInitialCurrencyContainer.setOnClickListener(new CurrencyChooseClickListener(true));
            mResultCurrencyContainer.setOnClickListener(new CurrencyChooseClickListener(false));
            mConvert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Editable text = mAmount.getText();
                    if (text != null)
                        mPresenter.exchange(text.toString());
                    else
                        Toast.makeText(getContext(), R.string.enter_amount, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class CurrencyChooseClickListener implements View.OnClickListener {

        private boolean mInitial;

        CurrencyChooseClickListener(boolean isInitial) {
            this.mInitial = isInitial;
        }

        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .hide(ExchangerFragment.this)
                        .add(R.id.fl_container, CurrenciesFragment.newInstance(mInitial))
                        .addToBackStack(null)
                        .commit();
            }
        }
    }
}
