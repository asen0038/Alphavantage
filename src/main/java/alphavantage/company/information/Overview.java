package alphavantage.company.information;

import java.util.List;

/**
 * The class encapsulating the
 * company overview information
 */
public interface Overview {

    /**
     * Returns a list of element field values
     *
     * @return A list of Elements
     */
    List<String> getElement();

    /**
     * Returns a list of value field values
     *
     * @return A list of Values
     */
    List<String> getValue();

}
