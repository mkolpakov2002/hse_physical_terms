package com.example.physicalterms.adapters;

import androidx.recyclerview.widget.DiffUtil;

import com.example.physicalterms.api.DefinitionRow;
import com.example.physicalterms.api.FormulaRow;

import java.util.List;

public class FormulaDiffUtilCallback extends DiffUtil.Callback{
    private List<FormulaRow> oldList;
    private List<FormulaRow> newList;

    public FormulaDiffUtilCallback(List<FormulaRow> oldList, List<FormulaRow> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    public FormulaDiffUtilCallback() {
    }

    /**
     * Returns the size of the old list.
     *
     * @return The size of the old list.
     */
    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    /**
     * Returns the size of the new list.
     *
     * @return The size of the new list.
     */
    @Override
    public int getNewListSize() {
        return newList.size();
    }

    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * <p>
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        FormulaRow oldDefinition = oldList.get(oldItemPosition);
        FormulaRow newDefinition = newList.get(newItemPosition);
        return oldDefinition.getId().equals(newDefinition.getId());
    }

    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * <p>
     * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
     * so that you can change its behavior depending on your UI.
     * For example, if you are using DiffUtil with a
     * {@link DefinitionAdapter RecyclerView.Adapter}, you should
     * return whether the items' visual representations are the same.
     * <p>
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     *
     * @param oldItemPosition The position of the item in the old list
     * @param newItemPosition The position of the item in the new list which replaces the
     *                        oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        FormulaRow oldDefinition = oldList.get(oldItemPosition);
        FormulaRow newDefinition = newList.get(newItemPosition);
        return oldDefinition.getName().equals(newDefinition.getName())
                && oldDefinition.getNameRus().equals(newDefinition.getNameRus())
                && oldDefinition.getComment().equals(newDefinition.getComment())
                && oldDefinition.getValue().equals(newDefinition.getValue())
                && oldDefinition.getSection().equals(newDefinition.getSection())
                && oldDefinition.getLanguage().equals(newDefinition.getLanguage())
                && oldDefinition.getCreatedAt().equals(newDefinition.getCreatedAt())
                && oldDefinition.getUpdatedAt().equals(newDefinition.getUpdatedAt());
    }
}
