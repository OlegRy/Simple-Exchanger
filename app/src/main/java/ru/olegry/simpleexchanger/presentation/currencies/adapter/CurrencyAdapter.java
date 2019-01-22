package ru.olegry.simpleexchanger.presentation.currencies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.olegry.simpleexchanger.R;
import ru.olegry.simpleexchanger.models.Currency;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private List<Currency> mCurrencies;
    private OnItemClickListener mListener;

    public CurrencyAdapter() {
        this(null);
    }

    CurrencyAdapter(List<Currency> currencies) {
        mCurrencies = currencies != null ? currencies : new ArrayList<Currency>();
    }

    public interface OnItemClickListener {
        void onItemClick(Currency chosenCurrency);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_currency, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Currency currency = mCurrencies.get(position);
        viewHolder.mCurrencyName.setText(currency.getCharCode());
        viewHolder.mCurrencyValue.setText(currency.getValue());
    }

    @Override
    public int getItemCount() {
        return mCurrencies.size();
    }

    public void update(List<Currency> newItems) {
        CurrencyDiffCallback diffCallback = new CurrencyDiffCallback(mCurrencies, newItems);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mCurrencies.clear();
        mCurrencies.addAll(newItems);

        diffResult.dispatchUpdatesTo(this);
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mCurrencyName;
        TextView mCurrencyValue;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCurrencyName = itemView.findViewById(R.id.tv_currency_name);
            mCurrencyValue = itemView.findViewById(R.id.tv_currency_value);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(mCurrencies.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
