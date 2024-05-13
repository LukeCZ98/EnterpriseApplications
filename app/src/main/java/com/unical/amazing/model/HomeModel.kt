package com.unical.amazing.model

data class HomeItem(val title: String, val description: String)

class HomeModel {
    fun getHomeItems(): List<HomeItem> {
        return listOf(
            HomeItem("Item 1", "Description 1"),
            HomeItem("Item 2", "Description 2"),
            HomeItem("Item 3", "Description 3")
        )
    }
}
