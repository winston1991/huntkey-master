package com.jake.huntkey.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.selected.BaseSelectFormat;

import com.bin.david.form.data.table.TableData;
import com.blankj.utilcode.util.ToastUtils;
import com.jake.huntkey.core.R;
import com.jake.huntkey.core.entity.ProductionLineEntity;

import java.util.ArrayList;

public class ProductionLineListViewAdapter extends BaseAdapter {
    private ArrayList<ArrayList<ProductionLineEntity>> datas;
    private LayoutInflater inflater;

    public ProductionLineListViewAdapter(Context context, ArrayList<ArrayList<ProductionLineEntity>> datas) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHold viewHold = null;
        if (convertView == null) {
            viewHold = new ViewHold();
            convertView = inflater.inflate(R.layout.production_line_listview_item_layout, null);
            viewHold.smartTable = convertView.findViewById(R.id.id_smart_table);
            viewHold.smartTable.getConfig().setShowYSequence(false);
            viewHold.smartTable.getConfig().setShowTableTitle(false);
            viewHold.smartTable.getConfig().setShowXSequence(false);
            viewHold.smartTable.getConfig().setHorizontalPadding(16);
            viewHold.smartTable.getConfig().setColumnTitleHorizontalPadding(17);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        if (position != 0) {
            viewHold.smartTable.getConfig().setShowColumnTitle(false);
        }else
        {
            viewHold.smartTable.getConfig().setShowColumnTitle(true);
        }
//        viewHold.smartTable.setSelectFormat(new BaseSelectFormat());
        viewHold.smartTable.setData(datas.get(position));
        viewHold.smartTable.getTableData().setOnItemClickListener(new TableData.OnItemClickListener() {
            @Override
            public void onClick(Column column, String value, Object o, int col, int row) {
                ToastUtils.showShort("col:" + col + "   row:" + row+"    postion:"+position);
            }
        });
        return convertView;
    }

    public static class ViewHold {
        public SmartTable<ProductionLineEntity> smartTable;
    }
}
