package com.example.homepc.topstories.model

/**
 * Created by HomePC on 6/1/2017.
 */

data class Channel (

        var title: String = "",
        var description: String = "",
        var link: String = "",
        var image: Image= Image(),
        var generator: String = "",
        var lastBuildDate: String = "",
        var copyright: String = "",
        var language: String = "",
        var ttl: String = "",
        var item: MutableList<Item> = mutableListOf<Item>()

)

data class Guid (

    var isPermaLink: String = "",
    var text: String = ""

)

data class Image (

    var url: String = "",
    var title: String = "",
    var link: String = ""

)

data class Item (

    var title: String = "",
    var description: String = "",
    var link: String = "",
    var guid: Guid = Guid(),
    var pubDate: String="",
    var mediaThumbnail: MediaThumbnail = MediaThumbnail()
)

data class MediaThumbnail (

    var width: String = "",
    var height: String = "",
    var url: String = ""

)

data class MyFeeds (

    var rss: Rss = Rss()

)

data class Rss (

    var xmlnsDc: String = "",
    var xmlnsContent: String = "",
    var xmlnsAtom: String = "",
    var xmlnsMedia: String = "",
    var version: String = "",
    var channel: Channel = Channel()

)