package com.revolut.money.transfer;

import com.revolut.money.transfer.infrastructure.JettyServerFactory;
import org.eclipse.jetty.server.Server;

public class Application {
    private Server server;

    public static void main(String[] args) throws Exception {
        new Application().start(8090, new DefaultDataPreparationFunction());
    }

    public void start(int port, DataPreparationFunction preparationFunction) throws InterruptedException {
        preparationFunction.prepareData();

        Thread serverThread = new Thread(() -> {
            try {
                server = JettyServerFactory.newServer(port);
                server.start();
            } catch (Exception e) {
                if (server != null) {
                    try {
                        server.stop();
                        server.destroy();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });
        serverThread.start();
        serverThread.join();
    }

    public void stop() throws Exception {
        if (server != null) {
            server.stop();
        }
    }
}
