package com.example.homepc.topstories

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.example.homepc.topstories.domain.FeedAdapter
import com.example.homepc.topstories.domain.ParseXML
import com.example.homepc.topstories.model.Item
import org.jetbrains.anko.*
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity(), AnkoLogger {

    var listView: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = find(R.id.xmlListView)

        doAsync {

            val feeds = useParseXml(downloadContents())

            uiThread {

                val feedAdapter = FeedAdapter(ctx, R.layout.list_record, feeds)

                listView?.adapter = feedAdapter


            }

        }

    }

    fun downloadContents(): InputStream {
        info("downloading..")
        val contents = URL("http://feeds.bbci.co.uk/news/health/rss.xml?edition=uk").readBytes()
        val contentsInputStream = contents.inputStream()
        return contentsInputStream

        //  val contents = BufferedInputStream(resources.openRawResource(R.raw.test)) // in xml format
        //  return contents
    }


    fun useParseXml(inputStream: InputStream): ArrayList<Item> {
        inputStream.use { inputStream ->

            val parseXml = ParseXML()
            parseXml.parseXMLAndStoreIt(inputStream)
            return parseXml.feeds
        }
    }


}
