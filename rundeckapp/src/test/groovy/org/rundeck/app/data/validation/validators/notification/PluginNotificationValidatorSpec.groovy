package org.rundeck.app.data.validation.validators.notification

import com.dtolabs.rundeck.core.common.IRundeckProject
import com.dtolabs.rundeck.core.plugins.DescribedPlugin
import com.dtolabs.rundeck.core.plugins.ValidatedPlugin
import com.dtolabs.rundeck.core.plugins.configuration.PropertyResolver
import com.dtolabs.rundeck.core.plugins.configuration.Validator
import com.dtolabs.rundeck.server.plugins.services.NotificationPluginProviderService
import org.rundeck.app.data.job.RdNotification
import rundeck.services.FrameworkService
import rundeck.services.PluginService
import spock.lang.Specification

class PluginNotificationValidatorSpec extends Specification {
    def "invalid when plugin type is missing"() {
        given:
        def fwkSvc = Mock(FrameworkService) {
            getPluginService() >> Mock(PluginService) {
                getPluginDescriptor(_,_) >> null
            }
            getFrameworkProject(_) >> Mock(IRundeckProject) {
                getProjectProperties( ) >> [:]
            }
        }
        PluginNotificationValidator validator = new PluginNotificationValidator(fwkSvc, "proj1")

        when:
        RdNotification n = new RdNotification()
        validator.validate(n, n.errors)

        then:
        n.errors.errorCount == 1
        n.errors.fieldErrors[0].code == "scheduledExecution.notifications.pluginTypeNotFound.message"
    }

    def "invalid when plugin configuration is bad"() {
        given:
        def fwkSvc = Mock(FrameworkService) {
            getPluginService() >> Mock(PluginService) {
                getPluginDescriptor(_,_) >> Mock(DescribedPlugin)
                createPluggableService(_) >> Mock(NotificationPluginProviderService)
                validatePlugin(_,_,_,_,_) >> new ValidatedPlugin(valid: false, report: new Validator.Report())
            }
            getFrameworkProject(_) >> Mock(IRundeckProject) {
                getProjectProperties( ) >> [:]
            }
            getFrameworkPropertyResolverWithProps(_,_) >> Mock(PropertyResolver)
        }
        PluginNotificationValidator validator = new PluginNotificationValidator(fwkSvc, "proj1")

        when:
        RdNotification n = new RdNotification()
        validator.validate(n, n.errors)

        then:
        n.errors.errorCount == 1
        n.errors.fieldErrors[0].code == "scheduledExecution.notifications.invalidPlugin.message"
    }
}
