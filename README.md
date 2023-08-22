# XMPP Chat Client

This project implements a chat client that supports the Extensible Messaging and Presence Protocol (XMPP) using Java, Maven, and the Smack library. The goal of the project is to understand how XMPP services work and to comprehend asynchronous programming fundamentals, often required in network development.

## Features

- Instant Messaging: Send and receive messages in real-time with any user connected to the XMPP server.
- File Transfer: Share and receive files with other users, which are stored locally.

## Installation

To run this project, follow these steps:

1. Clone the repository from GitHub using the following command:
git clone https://github.com/bar20807/chat_project

2. Make sure you have Java and Maven installed both on your system and in Visual Studio Code. If you don't have them installed, you can download and install them from the following links:

- [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

3. Once you have cloned the repository and installed the necessary dependencies, open the project in Visual Studio Code.

4. Run the project by clicking on "Run Java" in Visual Studio Code.

## Libraries

- **Smack:** An open-source XMPP (Jabber) client library for instant messaging and presence.

## Project Structure

The project has a simple structure:

- **`src/main/`**: This directory contains the source code for the project.
- **`src/test/`**: This directory contains the test files.

## Limitations

The client currently only supports text-based messages and file transfers. Future enhancements may include support for other XMPP features such as multi-user chat, presence information, message archive management, etc.

## Contributing

Contributions are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/)