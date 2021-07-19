package cn.rongcloud.quickstart.homepage;



import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.rtcdemo.R;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.HomeItemHolder> {

    public static class HomeItemHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvId;
        AppCompatTextView tvTitle;
        AppCompatTextView tvTime;

        public HomeItemHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }

    public static class HomeListItemModel {
        public int index;
        public String title;
        public String desc;

        public String icon;
        public View.OnClickListener clickListener;
        public HomeListItemModel(){}
        public HomeListItemModel(int index, String title, String description, String icon, View.OnClickListener onClickListener) {
            this.index = index;
            this.title = title;
            desc = description;
            this.icon = icon;
            clickListener = onClickListener;
        }
    }

    private final List<HomeListItemModel> modelList;

    public HomeListAdapter(List<HomeListItemModel> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public HomeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeItemHolder homeItemHolder, int position) {
        HomeListItemModel model = modelList.get(position);
        homeItemHolder.tvId.setText(model.icon);
        homeItemHolder.tvTitle.setText(model.title);
        homeItemHolder.tvTime.setText(model.desc);
        homeItemHolder.itemView.setOnClickListener(model.clickListener);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
