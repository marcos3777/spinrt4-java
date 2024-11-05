package br.com.fiap.mechai.controller;

import br.com.fiap.mechai.model.User;
import br.com.fiap.mechai.dao.UserDAO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.HttpStatus;

import java.io.IOException;


public class UserController {

    private UserDAO userDAO = new UserDAO();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void startServer() throws Exception {
        HttpServer server = HttpServer.createSimpleServer();


        server.getServerConfiguration().addHttpHandler(new UserHttpHandler(), "/users");


        server.start();
        System.out.println("Server started on port 8080...");


        System.in.read();
        server.shutdownNow();
    }

    private class UserHttpHandler extends HttpHandler {

        @Override
        public void service(Request request, Response response) throws Exception {
            String method = request.getMethod().getMethodString();

            switch (method) {
                case "GET":
                    handleGet(request, response);
                    break;
                case "POST":
                    handlePost(request, response);
                    break;
                case "PUT":
                    handlePut(request, response);
                    break;
                case "DELETE":
                    handleDelete(request, response);
                    break;
                default:
                    response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
                    response.getWriter().write("Method Not Allowed");
            }
        }

        private void handleGet(Request request, Response response) throws IOException {
            String idParam = request.getParameter("id");

            if (idParam != null) {

                int id = Integer.parseInt(idParam);
                User user = userDAO.readUser(id);

                if (user != null) {
                    String json = objectMapper.writeValueAsString(user);
                    response.setContentType("application/json");
                    response.getWriter().write(json);
                } else {
                    response.setStatus(HttpStatus.NOT_FOUND_404);
                    response.getWriter().write("User not found");
                }
            } else {

                String json = objectMapper.writeValueAsString(userDAO.getAllUsers());
                response.setContentType("application/json");
                response.getWriter().write(json);
            }
        }

        private void handlePost(Request request, Response response) throws IOException {
            String requestBody = request.getPostBody(1024).toStringContent();
            User user = objectMapper.readValue(requestBody, User.class);
            userDAO.createUser(user);
            response.setStatus(HttpStatus.CREATED_201);
            response.getWriter().write("User created successfully");
        }

        private void handlePut(Request request, Response response) throws IOException {
            String requestBody = request.getPostBody(1024).toStringContent();
            User user = objectMapper.readValue(requestBody, User.class);
            userDAO.updateUser(user);
            response.getWriter().write("User updated successfully");
        }

        private void handleDelete(Request request, Response response) throws IOException {
            String idParam = request.getParameter("id");

            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                userDAO.deleteUser(id);
                response.getWriter().write("User deleted successfully");
            } else {
                response.setStatus(HttpStatus.BAD_REQUEST_400);
                response.getWriter().write("User ID is required");
            }
        }
    }
}
