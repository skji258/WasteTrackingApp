package com.demo.sql.myapplication;

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var refreshButton: Button
    private lateinit var dataAdapter: DataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tableLayout = findViewById(R.id.tableLayout)
        refreshButton = findViewById(R.id.refreshButton)

        // Initialize the RecyclerView with an empty list
        dataAdapter = DataAdapter(emptyList())

        // Initial data fetch when the activity is created
        fetchDataFromApi()

        // Trigger data fetching when the refresh button is clicked
        refreshButton.setOnClickListener {
            fetchDataFromApi()
        }
    }

    private fun fetchDataFromApi() {
        FetchApiDataTask(this::onDataFetched).execute("http://10.0.2.2:8080/api/products")
    }

    private fun onDataFetched(jsonArray: JSONArray) {
        // Clear existing rows in the table
        tableLayout.removeAllViews()

        // Add header row
        val headerRow = layoutInflater.inflate(R.layout.table_row_item, null) as TableRow
        headerRow.findViewById<TextView>(R.id.txtRfid).text = "RFID"
        headerRow.findViewById<TextView>(R.id.txtProductType).text = "Product Type"
        headerRow.findViewById<TextView>(R.id.txtRecyclable).text = "Recyclable"
        headerRow.findViewById<TextView>(R.id.txtPercentage).text = "Percentage"
        tableLayout.addView(headerRow)

        // Add data rows
        val dataItems = parseJsonArray(jsonArray)
        for (dataItem in dataItems) {
            val dataRow = layoutInflater.inflate(R.layout.table_row_item, null) as TableRow
            dataRow.findViewById<TextView>(R.id.txtRfid).text = dataItem.rfid.toString()
            dataRow.findViewById<TextView>(R.id.txtProductType).text = dataItem.productType
            dataRow.findViewById<TextView>(R.id.txtRecyclable).text = dataItem.recyclable
            dataRow.findViewById<TextView>(R.id.txtPercentage).text = dataItem.percentage.toString()
            tableLayout.addView(dataRow)
        }
    }

    private fun parseJsonArray(jsonArray: JSONArray): List<DataItem> {
        val dataItems = mutableListOf<DataItem>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)

            val rfid = jsonObject.getInt("rfid")
            val productType = jsonObject.getString("productType")
            val recyclable = jsonObject.getString("recyclable")
            val percentage = jsonObject.getInt("percentage")

            val dataItem = DataItem(rfid, productType, recyclable, percentage)
            dataItems.add(dataItem)
        }

        return dataItems
    }

    // AsyncTask class...
    private class FetchApiDataTask(private val callback: (JSONArray) -> Unit) :
        AsyncTask<String, Void, JSONArray>() {

        override fun doInBackground(vararg params: String): JSONArray? {
            val urlString = params[0]

            try {
                val url = URL(urlString)
                val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

                try {
                    val inputStream = urlConnection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    return JSONArray(response.toString())
                } finally {
                    urlConnection.disconnect()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(result: JSONArray?) {
            result?.let { callback.invoke(it) }
        }
    }

}

