package com.javaprojetepitech.remy.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.javaprojetepitech.remy.myapplication.R;
import com.javaprojetepitech.remy.myapplication.model.Content;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {

    private List<Content> contentList;

    public ContentAdapter(List<Content> moviesList) {
        this.contentList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_row, parent, false);

        return new MyViewHolder(itemView);
    }

    // ON FILL ONE ITEM LIST
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Content content = contentList.get(position);

        holder.title.setText(content.getTitle());
        holder.description.setText(Html.fromHtml(content.getDescription()));

        String authors = "";
        for (int i = 0; i < content.getAuthors().size(); i++) {
            authors += content.getAuthors().get(i).getName();
            if (i < content.getAuthors().size() - 1) {
                authors += ", ";
            }
        }
        holder.author.setText(authors);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, author;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
            description = (TextView) view.findViewById(R.id.description);
            author = (TextView) view.findViewById(R.id.author);
        }
    }
}