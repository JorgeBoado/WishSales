package com.wishsales.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wishsales.R;

public class FundsDialogFragment extends DialogFragment {
    private double mSelectedFundAmount;
    private double mCurrentFundAmount;
    private Button m5Button;
    private Button m10Button;
    private Button m20Button;
    private TextView mCurrentFunds;
    private TextView mSelectedFunds;

    public static final String EXTRA_AMOUNT = "com.wishsales.fragments.extra_amount";
    public static final String ARG_CURRENT_FUNDS = "current_funds";

    public static FundsDialogFragment newInstance(Double currentFundAmount) {
        Bundle args = new Bundle();
        args.putDouble(ARG_CURRENT_FUNDS, currentFundAmount);

        FundsDialogFragment fragment = new FundsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_AMOUNT, mSelectedFundAmount);
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentFundAmount = getArguments().getDouble(ARG_CURRENT_FUNDS);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_funds, null);

        mCurrentFunds = (TextView) v.findViewById(R.id.funds_current_money);
        mCurrentFunds.setText(getString(R.string.funds_current_money) + String.valueOf(mCurrentFundAmount) + getString(R.string.badge));

        mSelectedFunds = (TextView) v.findViewById(R.id.funds_selected_value);
        mSelectedFunds.setText(getString(R.string.funds_selected_value) + String.valueOf(mSelectedFundAmount) + getString(R.string.badge));

        m5Button = (Button) v.findViewById(R.id.funds_add_five);
        m10Button = (Button) v.findViewById(R.id.funds_add_ten);
        m20Button = (Button) v.findViewById(R.id.funds_add_twenty);

        m5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedFundAmount = 5;
                updateUI();
            }
        });

        m10Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedFundAmount = 10;
                updateUI();
            }
        });

        m20Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedFundAmount = 20;
                updateUI();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(R.string.dialog_buy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .setNegativeButton(R.string.dialog_close, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_CANCELED);
                    }
                }).create();
    }

    private void updateUI() {
        mSelectedFunds.setText(getString(R.string.funds_selected_value) + String.valueOf(mSelectedFundAmount) + getString(R.string.badge));
    }
}
