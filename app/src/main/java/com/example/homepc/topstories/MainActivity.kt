package com.example.homepc.topstories

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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
    var feedUrl = healthNews
    var actionBar: ActionBar? = null
    var headingIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = find(R.id.xmlListView)
        actionBar = supportActionBar

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString("feedUrl")
            headingIndex = savedInstanceState.getInt("headingIndex")
        }
        setHeadingTitle(headingIndex)
        downloadFeedTask()



    }

    fun downloadFeedTask() {
        doAsync {

            val feeds = useParseXml(downloadContents())

            uiThread {

                val feedAdapter = FeedAdapter(ctx, R.layout.list_record, feeds)

                listView?.adapter = feedAdapter

                listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    val intent = Intent(ctx, WebViewActivity::class.java)
                    intent.putExtra("link", feeds[position].link)
                    startActivity(intent)
                }

            }

        }
    }

    fun downloadContents(): InputStream {
        info("downloading..")
        val contents = URL(feedUrl).readBytes()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//"Health News", "World News", "Science and Nature", "Technology", "Entertainment"
        when (item?.itemId) {
            R.id.menuHealthNews -> {
                feedUrl = healthNews
                setHeadingTitle(0)
            }
            R.id.menuEntertainment -> {
                feedUrl = entertainment
                setHeadingTitle(4)
            }
            R.id.menuScienceAndNature -> {
                feedUrl = scienceAndNature
                setHeadingTitle(2)
            }
            R.id.menuTechnology -> {
                feedUrl = technology
                setHeadingTitle(3)
            }
            R.id.menuWorldNews -> {
                feedUrl = worldNews
                setHeadingTitle(1)
            }
        }

        downloadFeedTask()

        return super.onOptionsItemSelected(item)
    }

    private fun setHeadingTitle(index: Int) {
        actionBar?.title = headings[index]
        headingIndex = index
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString("feedUrl", feedUrl)
        outState?.putInt("headingIndex", headingIndex)
        super.onSaveInstanceState(outState)
    }



    companion object {
        val healthNews = "http://feeds.bbci.co.uk/news/health/rss.xml?edition=uk"
        val worldNews = "http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk"
        val scienceAndNature = "http://feeds.bbci.co.uk/news/science_and_environment/rss.xml?edition=uk"
        val technology = "http://feeds.bbci.co.uk/news/technology/rss.xml?edition=uk"
        val entertainment = "http://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml?edition=uk"
        val headings = listOf("Health News", "World News", "Science and Nature", "Technology", "Entertainment")
    }
}
