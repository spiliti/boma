/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2018  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 *
 */

package org.egov.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class EGovConfig. Used to read the values from properties file and XMl configuration files
 *
 * @author Manu Srivastava
 * @deprecated no longer supported
 */
@Deprecated
public final class EGovConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(EGovConfig.class);

    private static Map<String, XMLConfiguration> configurationMap = new ConcurrentHashMap<>();
    private static Map<String, Properties> propertiesMap = new ConcurrentHashMap<>();

    private EGovConfig() {
    }

    /**
     * Initialize class variable.
     *
     * @param xmlFileName the xml file name
     * @return the xML configuration
     */
    private static XMLConfiguration initializeClassVariable(final String xmlFileName) {
        synchronized (EGovConfig.class) {
            XMLConfiguration configuration = null;
            try {
                if (configurationMap.get(xmlFileName) == null) {
                    configuration = new XMLConfiguration(toURL(xmlFileName));
                    configurationMap.put(xmlFileName, configuration);
                } else {
                    configuration = configurationMap.get(xmlFileName);
                }
                return configuration;
            } catch (final ConfigurationException cex) {
                LOGGER.error("Error occurred in initializeClassVariable", cex);
                throw new ApplicationRuntimeException("Error occurred in initializeClassVariable", cex);
            }
        }

    }

    /**
     * To url.
     *
     * @param xmlFileName the xml file name
     * @return the uRL
     */
    private static URL toURL(final String xmlFileName) {
        return (xmlFileName.startsWith("config/")) ? Thread.currentThread().getContextClassLoader().getResource(xmlFileName)
                : Thread.currentThread().getContextClassLoader().getResource("config/" + xmlFileName);

    }

    /**
     * Gets the XML configuration.
     *
     * @param xmlFileName the xml file name
     * @return the xML configuration
     */
    private static XMLConfiguration getXMLConfiguration(final String xmlFileName) {
        return configurationMap.get(xmlFileName) == null ? initializeClassVariable(xmlFileName) : configurationMap.get(xmlFileName);

    }

    /**
     * This is the real implementation. It will return the value of the property for a given XML file.
     * First of all, it checks if the name of the category exists. If not, then it will use the name of the default category.
     * The next step is that it will look for the property. If it is not found in the
     * category, it will look inside the default category (inheritance).
     * If it still cannot find the property, it will return the defaultValue.
     *
     * @param xmlFileName  - name of the XML configuration file
     * @param key          - name of the property to searcfor
     * @param defaultValue - the defaultValue that will be returned if the property cannot be found
     * @param categoryName - the name of the category
     * @return String
     */
    public static String getProperty(final String xmlFileName, final String key, final String defaultValue, final String categoryName) {
        try {
            final XMLConfiguration configurationXML = getXMLConfiguration(xmlFileName);
            final String output = configurationXML.getString(categoryName + "." + key);
            return output == null ? defaultValue : output;
        } catch (final Exception exp) {
            LOGGER.error("Error occurred in while getting property from given xml file", exp);
            throw new ApplicationRuntimeException("Error occurred in while getting property from given xml file", exp);
        }

    }

    /**
     * This method returns the String message based on the given properties file name and message key.
     *
     * @param filename   name of the properties file
     * @param messagekey name of the message key to search for
     * @return String
     */
    public static String getMessage(final String filename, final String messageKey) {
        Properties properties = new Properties();
        try {
            if (propertiesMap.get(filename) == null) {
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
                propertiesMap.put(filename, properties);
            }
            properties = propertiesMap.get(filename);
        } catch (final Exception exp) {
            LOGGER.error("Error Loading Properties File", exp);
        }
        return properties.getProperty(messageKey);
    }


}
