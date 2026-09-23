package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final List<PantryItem> pantryItems;
    private final DatabaseHelper databaseHelper;
    private final Context context;

    private static final String PREFS_NAME =
            "SmartPantrySettings";

    private static final String KEY_EXPIRY_ALERTS =
            "expiry_alerts";

    public PantryAdapter(
            Context context,
            List<PantryItem> pantryItems) {

        this.context = context;
        this.pantryItems = pantryItems;

        this.databaseHelper =
                new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_pantry,
                                parent,
                                false
                        );

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        PantryItem item =
                pantryItems.get(position);

        holder.tvIngredientName.setText(
                item.getName()
        );

        String quantityText =
                item.getQuantity()
                        + " "
                        + item.getUnit();

        holder.tvIngredientQuantity.setText(
                quantityText
        );

        String expiryDate =
                item.getExpiryDate();

        if (expiryDate != null &&
                !expiryDate.isEmpty()) {

            holder.tvIngredientExpiry.setText(
                    "Expires: " + expiryDate
            );

            checkExpiryAlert(
                    holder,
                    expiryDate
            );

        } else {

            holder.tvIngredientExpiry.setText(
                    "No expiry date"
            );

            holder.tvIngredientExpiry
                    .setTextColor(
                            Color.DKGRAY
                    );
        }

        // EDIT INGREDIENT
        holder.btnEditIngredient.setOnClickListener(
                v -> {

                    Intent intent =
                            new Intent(
                                    holder.itemView.getContext(),
                                    AddEditIngredientActivity.class
                            );

                    intent.putExtra(
                            "ingredient_id",
                            item.getId()
                    );

                    intent.putExtra(
                            "ingredient_name",
                            item.getName()
                    );

                    intent.putExtra(
                            "ingredient_quantity",
                            item.getQuantity()
                    );

                    intent.putExtra(
                            "ingredient_unit",
                            item.getUnit()
                    );

                    intent.putExtra(
                            "ingredient_expiry",
                            item.getExpiryDate()
                    );

                    holder.itemView.getContext()
                            .startActivity(intent);
                }
        );

        // DELETE INGREDIENT
        holder.btnDeleteIngredient.setOnClickListener(
                v -> {

                    int adapterPosition =
                            holder.getAdapterPosition();

                    if (adapterPosition !=
                            RecyclerView.NO_POSITION) {

                        PantryItem currentItem =
                                pantryItems.get(
                                        adapterPosition
                                );

                        new android.app.AlertDialog.Builder(
                                holder.itemView.getContext()
                        )
                                .setTitle(
                                        "Delete Ingredient"
                                )
                                .setMessage(
                                        "Are you sure you want to delete "
                                                + currentItem.getName()
                                                + "?"
                                )
                                .setPositiveButton(
                                        "Delete",
                                        (dialog, which) -> {

                                            databaseHelper
                                                    .deletePantryItem(
                                                            currentItem.getId()
                                                    );

                                            pantryItems.remove(
                                                    adapterPosition
                                            );

                                            notifyItemRemoved(
                                                    adapterPosition
                                            );
                                        }
                                )
                                .setNegativeButton(
                                        "Cancel",
                                        null
                                )
                                .show();
                    }
                }
        );
    }

    private void checkExpiryAlert(
            PantryViewHolder holder,
            String expiryDate) {

        SharedPreferences preferences =
                context.getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                );

        boolean expiryAlertsEnabled =
                preferences.getBoolean(
                        KEY_EXPIRY_ALERTS,
                        false
                );

        holder.tvIngredientExpiry.setText(
                "Expires: " + expiryDate
        );

        holder.tvIngredientExpiry.setTextColor(
                Color.DKGRAY
        );

        if (!expiryAlertsEnabled) {
            return;
        }

        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        "dd MMMM yyyy",
                        Locale.ENGLISH
                );

        dateFormat.setLenient(false);

        try {

            Date expiry =
                    dateFormat.parse(
                            expiryDate
                    );

            if (expiry == null) {
                return;
            }

            Date today =
                    new Date();

            long difference =
                    expiry.getTime()
                            - today.getTime();

            long daysUntilExpiry =
                    TimeUnit.MILLISECONDS.toDays(
                            difference
                    );

            if (difference < 0) {

                holder.tvIngredientExpiry.setText(
                        "⚠ EXPIRED: " + expiryDate
                );

                holder.tvIngredientExpiry
                        .setTextColor(
                                Color.RED
                        );

            } else if (daysUntilExpiry <= 7) {

                holder.tvIngredientExpiry.setText(
                        "⚠ Expires soon: " + expiryDate
                );

                holder.tvIngredientExpiry
                        .setTextColor(
                                0xFFFF8800
                        );
            }

        } catch (ParseException e) {

            holder.tvIngredientExpiry.setText(
                    "Expires: " + expiryDate
            );
        }
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvIngredientName;
        TextView tvIngredientQuantity;
        TextView tvIngredientExpiry;

        Button btnEditIngredient;
        Button btnDeleteIngredient;

        public PantryViewHolder(
                @NonNull View itemView) {

            super(itemView);

            tvIngredientName =
                    itemView.findViewById(
                            R.id.tvIngredientName
                    );

            tvIngredientQuantity =
                    itemView.findViewById(
                            R.id.tvIngredientQuantity
                    );

            tvIngredientExpiry =
                    itemView.findViewById(
                            R.id.tvIngredientExpiry
                    );

            btnEditIngredient =
                    itemView.findViewById(
                            R.id.btnEditIngredient
                    );

            btnDeleteIngredient =
                    itemView.findViewById(
                            R.id.btnDeleteIngredient
                    );
        }
    }
}