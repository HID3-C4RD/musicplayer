/*
 * Copyright (c) 2019 Naman Dwivedi.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 */
package com.technicalsaqii.softmp3.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import com.technicalsaqii.softmp3.R
import com.technicalsaqii.softmp3.extensions.addOnItemClick
import com.technicalsaqii.softmp3.extensions.drawable
import com.technicalsaqii.softmp3.extensions.filter
import com.technicalsaqii.softmp3.extensions.inflateTo
import com.technicalsaqii.softmp3.extensions.observe
import com.technicalsaqii.softmp3.extensions.safeActivity
import com.technicalsaqii.softmp3.models.Playlist
import com.technicalsaqii.softmp3.ui.adapters.PlaylistAdapter
import com.technicalsaqii.softmp3.ui.dialogs.CreatePlaylistDialog
import com.technicalsaqii.softmp3.ui.fragments.base.MediaItemFragment
import kotlinx.android.synthetic.main.fragment_playlists.btnNewPlaylist
import kotlinx.android.synthetic.main.fragment_playlists.recyclerView

class PlaylistFragment : MediaItemFragment(), CreatePlaylistDialog.PlaylistCreatedCallback {
    private lateinit var playlistAdapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflateTo(R.layout.fragment_playlists, container)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        playlistAdapter = PlaylistAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(safeActivity)
            adapter = playlistAdapter
            addItemDecoration(DividerItemDecoration(safeActivity, VERTICAL).apply {
                setDrawable(safeActivity.drawable(R.drawable.divider)!!)
            })
            addOnItemClick { position, _ ->
                mainViewModel.mediaItemClicked(playlistAdapter.playlists[position], null)
            }
        }

        mediaItemFragmentViewModel.mediaItems
                .filter { it.isNotEmpty() }
                .observe(this) { list ->
                    @Suppress("UNCHECKED_CAST")
                    playlistAdapter.updateData(list as List<Playlist>)
                }

        btnNewPlaylist.setOnClickListener {
            CreatePlaylistDialog.show(this@PlaylistFragment)
        }
    }

    override fun onPlaylistCreated() = mediaItemFragmentViewModel.reloadMediaItems()
}
