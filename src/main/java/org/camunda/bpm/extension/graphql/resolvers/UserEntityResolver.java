package org.camunda.bpm.extension.graphql.resolvers;

import com.coxautodev.graphql.tools.GraphQLResolver;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.GroupQuery;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
public class UserEntityResolver implements GraphQLResolver<UserEntity> {
    private final static Logger LOGGER = Logger.getLogger("UserEntityResolver");

    @Autowired
    TaskService taskService;

    @Autowired
    IdentityService identityService;

    public UserEntityResolver() {

    }

    public List<Task> tasks(User user) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.taskAssignee(user.getId());
        taskQuery.initializeFormKeys();
        List<Task> tasks = taskQuery.list();
        return tasks;
    }

    public List<Group> groups(User user) {
        GroupQuery groupQuery = identityService.createGroupQuery();
        groupQuery.groupMember(user.getId());
        List<Group> groups = groupQuery.list();
        return groups;
    }

}
