package com.example.homepc.topstories.domain

import com.example.homepc.topstories.model.Item
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by HomePC on 6/1/2017.
 */
class ParseXML : AnkoLogger {
    val feeds = arrayListOf<Item>()

    fun parseXMLAndStoreIt(xmlData: InputStream): Boolean {
        var status = true
        var currentRecord = Item()
        var inItem = false
        var textValue = ""

        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            val xpp = factory.newPullParser()
            //   xpp.setInput(StringReader(xmlData))
            xpp.setInput(xmlData.reader())

            var eventType = xpp.eventType

            while (eventType != XmlPullParser.END_DOCUMENT) {

                val tagName = xpp.name
                when (eventType) {

                    XmlPullParser.START_TAG -> {
                        info("parse: Starting tag for $tagName")
                        if ("item".equals(tagName, true)) {
                            inItem = true
                            currentRecord = Item()
                        }
                    }

                    XmlPullParser.TEXT -> textValue = xpp.text

                    XmlPullParser.END_TAG -> {
                        info("parse: Ending tag for $tagName")
                        if (inItem) {
                            if ("item".equals(tagName, true)) {
                                feeds.add(currentRecord)
                                inItem = false
                            } else if ("description".equals(tagName, true)) {
                                currentRecord.description = textValue
                            } else if ("title".equals(tagName, true)) {
                                currentRecord.title = textValue
                            } else if ("link".equals(tagName, true)) {
                                currentRecord.link = textValue
                            } else if ("pubDate".equals(tagName, true)) {
                                currentRecord.pubDate = formatToShorterDate(textValue)
                            }

                        }
                    } // XmlPullParser.END_TAG
                }

                eventType = xpp.next()

            }
            for (feed in feeds) {
                info("********************************")
                info(feed.toString())
            }

        } catch (e: Exception) {
            e.printStackTrace()
            status = false
        }

        return status
    }

    private fun formatToShorterDate(dateString: String) : String {
        // Fri, 06 Jan 2017 02:03:51 GMT
        val date = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z").parse(dateString)
        val newDateString = SimpleDateFormat("dd-MM-yyyy").format(date)
      //  return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(dateString)
        return newDateString
    }
}