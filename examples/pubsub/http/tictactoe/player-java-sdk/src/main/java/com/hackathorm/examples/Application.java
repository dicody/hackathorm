package com.hackathorm.examples;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addRequiredOption("p", "port", true, "The port this app will listen on");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        // If port string is not valid, it will throw an exception.
        int port = Integer.parseInt(cmd.getOptionValue("port"));

        // Start Dapr's callback endpoint.
        start(port);
    }

    public static void start(int port) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(String.format("--server.port=%d", port));
    }
}
