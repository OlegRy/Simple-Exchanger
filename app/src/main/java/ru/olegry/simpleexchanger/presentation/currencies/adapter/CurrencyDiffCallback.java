package ru.olegry.simpleexchanger.presentation.currencies.adapter;

import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import ru.olegry.simpleexchanger.models.Currency;

public class CurrencyDiffCallback extends DiffUtil.Callback {

    private List<Currency> mOldItems;
    private List<Currency> mNewItems;

    public CurrencyDiffCallback(List<Currency> oldItems, List<Currency> newItems) {
        mOldItems = oldItems != null ? new ArrayList<>(oldItems) : new ArrayList<Currency>();
        mNewItems = newItems != null ? new ArrayList<>(newItems) : new ArrayList<Currency>();
    }

    @Override
    public int getOldListSize() {
        return mOldItems.size();
    }

    @Override
    public int getNewListSize() {
        return mNewItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return TextUtils.equals(mOldItems.get(oldItemPosition).getId(), mNewItems.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).equals(mNewItems.get(newItemPosition));
    }
}
