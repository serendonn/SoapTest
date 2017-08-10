/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Sion Williams
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.byteshifter.plugins.soapui.tasks

import com.eviware.soapui.SoapUI
import com.eviware.soapui.tools.SoapUILoadTestRunner
import org.gradle.api.tasks.Optional

/**
 * Runs soapUI load tests
 * task name - loadtest
 *
 * @author Sion Williams
 */
class LoadTestTask extends SoapUITask {

    /**
     * The TestSuite to run
     */
    @Optional
    String testSuite

    /**
     * The TestCase to run
     */
    @Optional
    String testCase

    /**
     * The LoadTest to run
     */
    String loadTest

    /**
     * The username to use for authentication challenges
     */
    @Optional
    String username

    /**
     * The password to use for authentication challenges
     */
    @Optional
    String password

    /**
     * The WSS password-type to use for any authentications. Setting this will result
     * in the addition of WS-Security UsernamePassword tokens to any outgoing request containing
     * the specified username and password. Set to either 'Text' or 'Digest'
     */
    @Optional
    String wssPasswordType

    /**
     * The domain to use for authentication challenges
     */
    @Optional
    String domain

    /**
     * The host to use for requests
     */
    @Optional
    String host

    /**
     * Overrides the endpoint to use for requests
     */
    @Optional
    String endpoint

    /**
     * Overrides the LoadTest limit
     */
    @Optional
    Integer limit

    /**
     * Overrides the LoadTest threadCount
     */
    @Optional
    Integer threadCount

    /**
     * Sets the output folder for reports
     */
    @Optional
    String outputFolder

    /**
     * Turns on printing of reports
     */
    boolean printReport

    /**
     * Tells Test Runner to skip tests.
     */
    @Optional
    boolean skip

    /**
     * Specified global property values
     */
    @Optional
    String[] globalProperties

    /**
     * Specified project property values
     */
    @Optional
    String[] projectProperties

    /**
     * Saves project file after running tests
     */
    boolean saveAfterRun

    /**
     * SoapUI Properties.
     */
    @Optional
    Properties soapuiProperties

    LoadTestTask() {
        super('Runs soapUI load tests.')
    }

    @Override
    public void executeAction() {
        SoapUILoadTestRunner runner = new SoapUILoadTestRunner(
                'soapUI ' + SoapUI.SOAPUI_VERSION + ' Gradle LoadTest Runner')
        runner.projectFile = projectFile

        if (endpoint) {
            runner.endpoint = endpoint
        }

        if (testSuite) {
            runner.testSuite = testSuite
        }

        if (testCase) {
            runner.testCase = testCase
        }

        if (username) {
            runner.username = username
        }

        if (password) {
            runner.password = password
        }

        if (wssPasswordType) {
            runner.wssPasswordType = wssPasswordType
        }

        if (domain) {
            runner.domain = domain
        }

        if (limit) {
            runner.limit = limit.intValue()
        }

        if (threadCount) {
            runner.threadCount = threadCount.intValue()
        }

        if (host) {
            runner.host = host
        }

        if (outputFolder) {
            runner.outputFolder = outputFolder
        }

        runner.printReport = printReport
        runner.saveAfterRun = saveAfterRun

        if (settingsFile) {
            runner.settingsFile = settingsFile
        }

        if (projectPassword) {
            runner.projectPassword = projectPassword
        }

        if (settingsPassword) {
            runner.soapUISettingsPassword = settingsPassword
        }

        if (globalProperties) {
            runner.globalProperties = globalProperties
        }

        if (projectProperties) {
            runner.projectProperties = projectProperties
        }

        if (soapuiProperties != null && soapuiProperties.size() > 0) {
            for (Object key : soapuiProperties.keySet()) {
                println('Setting ' + (String) key + ' value ' + soapuiProperties.getProperty((String) key))
                System.setProperty((String) key, soapuiProperties.getProperty((String) key))
            }
        }

        runner.run()
    }
}
