package com.openclassrooms.realestatemanager.addproperty

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Agent
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.io.File
import java.io.Serializable

class ListAgentSpinnerAdapter(val context: Context, var agents: List<Agent>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_agent_dialog_item, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }

        // For name
        val nameToFirstName = agents[position].firstName
        val nameToLastName = agents[position].lastName

        val nameToDisplay = "$nameToFirstName $nameToLastName"

        vh.listAgentRvName.text = nameToDisplay

        // For picture
        val idAgentPicture = agents[position].agentId

        Picasso
            .get()
            .load(File("/data/data/com.openclassrooms.realestatemanager/files/", "$idAgentPicture.jpg"))
            .transform(CropCircleTransformation())
            .placeholder(R.drawable.agent_picture_default)
            .into(vh.listAgentRvPicture)

        return view
    }

    override fun getItem(position: Int): Any {
        return agents[position]
    }

    override fun getCount(): Int {
        return agents.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getPosition(item: String): Int {
        return agents.indexOf<Serializable>(item)
    }

    private class ItemHolder(row: View?) {
        val listAgentRvName: TextView
        val listAgentRvPicture: ImageView

        init {
            this.listAgentRvName = row?.findViewById(R.id.list_agent_rv_name) as TextView
            this.listAgentRvPicture = row.findViewById(R.id.list_agent_rv_picture) as ImageView
        }
    }
}