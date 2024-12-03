package com.example.taskmanager.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.RoleService;
import com.example.taskmanager.service.TaskService;
import com.example.taskmanager.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private TaskService taskService;
    private RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Value("${default.admin.mail}")
    private String defaultAdminMail;
    @Value("${default.admin.name}")
    private String defaultAdminName;
    @Value("${default.admin.password}")
    private String defaultAdminPassword;
    @Value("${default.admin.image}")
    private String defaultAdminImage;

    @Autowired
    public InitialDataLoader(UserService userService, TaskService taskService, RoleService roleService) {
        this.userService = userService;
        this.taskService = taskService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //ROLES --------------------------------------------------------------------------------------------------------
        roleService.createRole(new Role("ADMIN"));
        roleService.createRole(new Role("USER"));
        roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);

        //USERS --------------------------------------------------------------------------------------------------------
        //1
        User admin = new User(
                defaultAdminMail,
                defaultAdminName,
                defaultAdminPassword,
                defaultAdminImage);
        userService.createUser(admin);
        userService.changeRoleToAdmin(admin);

        //2
        User manager = new User(
                "manager@mail.com",
                "Manager",
                "112233",
                "images/admin.png");
        userService.createUser(manager);
        userService.changeRoleToAdmin(manager);

        //3
        userService.createUser(new User(
                "kartikeya@mail.com",
                "Kartikeya",
                "112233",
                ""));

        //4
        userService.createUser(new User(
                "aishwarya@mail.com",
                "Aishwarya",
                "112233",
                ""));

        //5
        userService.createUser(new User(
                "ritesh@mail.com",
                "Ritesh",
                "112233",
                ""));

        //6
        userService.createUser(new User(
                "avdhut@mail.com",
                "Avdhut",
                "112233",
                ""));

        //7
        userService.createUser(new User(
                "ankita@mail.com",
                "Ankita",
                "112233",
                ""));

        userService.findAll().stream()
                .map(u -> "saved user: " + u.getName())
                .forEach(logger::info);


        //TASKS --------------------------------------------------------------------------------------------------------
        LocalDate today = LocalDate.now();

        //1
        taskService.createTask(new Task(
                "Collect briefing document ",
                "Setup first meeting with client. Collect basic data about the client. Define and collect briefing document from client.",
                today.minusDays(40),
                true,
                userService.getUserByEmail("kartikeya@mail.com").getName(),
                userService.getUserByEmail("kartikeya@mail.com")
        ));

        //2
        taskService.createTask(new Task(
                "Define project questionnaire ",
                "Define and send project questionnaire to the client and wait for the client’s response. Iterate on doubts you have until everybody is in agreement. Finalize project questionnaire from client.",
                today.minusDays(30),
                true,
                userService.getUserByEmail("aishwarya@mail.com").getName(),
                userService.getUserByEmail("aishwarya@mail.com")
        ));

        //3
        taskService.createTask(new Task(
                "Research client’s company and industry",
                "Research client’s company to understand their brand, the way they communicate, their demographics, target audience. Research client’s industry to find ways of communicating specifically to the industry, strengths and weaknesses, trends and other industry specifics.",
                today.minusDays(20),
                true,
                userService.getUserByEmail("ritesh@mail.com").getName(),
                userService.getUserByEmail("ritesh@mail.com")
        ));

        //4
        taskService.createTask(new Task(
                "Get quotation for project",
                "Get quotation for development effort for project. Estimate design work with designers. Get quotation for copy/content or estimate work with your copywriters.  Get quotation for photography and video production or estimate effort involved.",
                today.minusDays(10),
                true,
                userService.getUserByEmail("avdhut@mail.com").getName(),
                userService.getUserByEmail("avdhut@mail.com")
        ));

        //5
        taskService.createTask(new Task(
                "Get quotation for hosting and domain",
                "Get quotation for hosting and domain, particularly if specialized hosting is involved such as VPS hosting, cloud hosting, or special hosting or environment requirements.",
                today.minusDays(5),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("avdhut@mail.com")
        ));

        //6
        taskService.createTask(new Task(
                "Check the quality of each content element",
                "Quality assure each piece of content you have outsourced or bought – and ask for changes where necessary. Populate the website content with the various content items you have agreed with the client.",
                today.minusDays(2),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("kartikeya@mail.com")
        ));

        //7
        taskService.createTask(new Task(
                "Define a Contact Us page and social media details",
                "Define a Contact Us page with correct client details and a map. Populate links and icongraphy with links to relevant social media details. Create a link to your website in the footer (make sure it has been agreed with the client to do so).",
                today.minusDays(1),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("kartikeya@mail.com")
        ));

        //8
        taskService.createTask(new Task(
                "Check all web copywriting",
                "Make sure web copywriting has been proofread and ran through a spelling and grammar checker to check for correctness. Use online tools such as Reverso, or Spellcheckplus.com. Check that generic content, such as lorem ipsum, has been properly removed and replaced.",
                today,
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("aishwarya@mail.com")
        ));

        //9
        taskService.createTask(new Task(
                "Check all images and videos",
                "See that all images are in the correct places, smushed, formatted, width and height specified and working on all devices. Confirm that videos and audio files are in the correct places, formatted and working on all devices.",
                today.plusDays(1),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("aishwarya@mail.com")
        ));

        //10
        taskService.createTask(new Task(
                "Check all linked content",
                "Test all linked content, such as case studies, ebooks, and whitepapers, and verify that they are correctly linked. Test to see that all internal links across web pages are working properly. Ensure that company logo is linked to the homepage.",
                today.plusDays(2),
                false,
                userService.getUserByEmail("manager@mail.com").getName(),
                userService.getUserByEmail("avdhut@mail.com")
        ));

        taskService.findAll().stream().map(t -> "saved task: '" + t.getName()
                + "' for owner: " + getOwnerNameOrNoOwner(t)).forEach(logger::info);
    }

    private String getOwnerNameOrNoOwner(Task task) {
        return task.getOwner() == null ? "no owner" : task.getOwner().getName();
    }
}
