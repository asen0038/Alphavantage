package alphavantage.message;

import alphavantage.company.Company;

/**
 * This class is the message sender service
 * that sets up the message body and communicates
 * with the output API
 */
public interface Message {

    /**
     * Reads from JSON file configure/credentials.json
     * the sid and api key details and phone numbers
     *
     * @throws IllegalStateException if details in the configure/credentials are not entered correctly
     */
    void setDetails() throws IllegalStateException;

    /**
     * Configures the message using the Company
     * object c and tailors the message body
     * so it can be sent in a suitable manner
     *
     * @param c
     */
    void configureMessage(Company c);

    /**
     * Communicates with the API and
     * sends a POST request to send the
     * text message to the configured
     * phone number
     *
     * @param type
     * @throws IllegalStateException if the message is not sent succesfully
     */
    void sendMessage(String type) throws IllegalStateException;

}
