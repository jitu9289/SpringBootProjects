package com.corona.tracker.service;

import com.corona.tracker.Model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidService {
    public static String virus_data_url ="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LocationStats> allStats=new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public  void fetchCovidData() throws IOException, InterruptedException {
        List<LocationStats> newStats=new ArrayList<>();
        HttpClient client =HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(virus_data_url)).build();
        HttpResponse<String> httpResponse =client.send(request,HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.body());

        StringReader csvBodyReader =new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for (CSVRecord record : records) {
            LocationStats locationStats =new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int latest=Integer.parseInt(record.get(record.size()-1));
            int previous=Integer.parseInt(record.get(record.size()-2));
            locationStats.setLatestTotalCases(latest);
            locationStats.setDiffPrevDay(latest-previous);
//            System.out.println(locationStats);
            newStats.add(locationStats);
        }
        this.allStats=newStats;
    }
}
