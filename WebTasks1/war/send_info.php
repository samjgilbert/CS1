<?php
$to = $_GET['to'];
$subject = "UCL Mechanical Turk Experiment: Information Sheet";
$headers = "From: Sam Gilbert <sam.gilbert@ucl.ac.uk>";

$body = "Title of project: Online response time studies of attention and memory

This study has been approved by the UCL Research Ethics Committee as Project ID Number: 1584/002

Name, address and contact details of investigators:
Dr Sam Gilbert
Institute of Cognitive Neuroscience
17 Queen Square
London WC1N 3AR

sam.gilbert@ucl.ac.uk

We would like to invite you to participate in this research project. You should only participate if you want to; choosing not to take part will not disadvantage you in any way. Before you decide whether you want to take part, please read the following information carefully and discuss it with others if you wish. Ask us if there is anything that is not clear or you would like more information.

We are recruiting volunteers from the Amazon Mechanical Turk website to take part in an experiment aiming to improve our understanding of human attention and memory. In this experiment you will see see some simple objects on the screen. You will be asked to use your mouse to drag these objects to different parts of the screen. You will also be asked to answer some simple questions about yourself and about your experience of the tasks. Full instructions will be provided before the experiment begins. The experiment will last approximately 15 minutes. There are no anticipated risks or benefits associated with participation in this study.

It is up to you to decide whether or not to take part. If you choose not to participate, you won't incur any penalties or lose any benefits to which you might have been entitled. However, if you do decide to take part, you can print out this information sheet and you will be asked to fill out a consent form on the next page. Even after agreeing to take part, you can still withdraw at any time and without giving a reason. 

All data will be collected and stored in accordance with the UK Data Protection Act 1998.";


if (mail($to, $subject, $body,$headers)) {} else {
echo("<p>Message delivery failed...</p>");
}
?>