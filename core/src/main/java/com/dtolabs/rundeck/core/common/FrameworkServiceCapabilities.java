package com.dtolabs.rundeck.core.common;

import com.dtolabs.rundeck.core.authorization.AuthContext;
import com.dtolabs.rundeck.core.config.FeatureService;
import com.dtolabs.rundeck.core.execution.workflow.WorkflowExecutionItemFactory;
import com.dtolabs.rundeck.core.jobs.IExecutionLifecyclePluginService;
import com.dtolabs.rundeck.core.options.JobOptionUrlExpander;
import com.dtolabs.rundeck.core.options.RemoteJsonOptionRetriever;
import com.dtolabs.rundeck.core.plugins.PluginServiceCapabilities;
import com.dtolabs.rundeck.core.plugins.configuration.Description;
import com.dtolabs.rundeck.core.plugins.configuration.PropertyResolver;
import com.dtolabs.rundeck.core.plugins.configuration.PropertyScope;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Map;

public interface FrameworkServiceCapabilities {

    PluginServiceCapabilities getPluginService();
    IFramework getRundeckFramework();
    FeatureService getFeatureService();
    boolean existsFrameworkProject(String project);
    IRundeckProject getFrameworkProject(String project);
    PropertyResolver getFrameworkPropertyResolverWithProps(Map projectProps, Map instanceConfig);
    Collection<String> projectNames(AuthContext authContext);
    AuthContext userAuthContext(HttpSession session);
    JobOptionUrlExpander getJobOptionUrlExpander();
    RemoteJsonOptionRetriever getRemoteJsonOptionRetriever();
    WorkflowExecutionItemFactory getWorkflowExecutionItemFactory();
    IExecutionLifecyclePluginService getExecutionLifecyclePluginService();


    Map validateDescription(Description description, String prefix, Map params);
    Map validateDescription(
            Description description,
            String prefix,
            Map params,
            String project,
            PropertyScope defaultScope,
            PropertyScope ignored);
}
