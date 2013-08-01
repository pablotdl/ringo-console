<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  
  <head>
    <title>Ringo: Monitoring Console</title>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="<c:url value="/css/style.css" />" rel="stylesheet" />
    <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet" />

    <link rel="stylesheet" href="<c:url value="/css/jquery-ui-1.8.16.custom.css" />" media="screen"  />
    <link rel="stylesheet" href="<c:url value="/css/fullcalendar.css" />" media="screen"  />
    <link rel="stylesheet" href="<c:url value="/css/chosen.css" />" media="screen"  />
    <link rel="stylesheet" href="<c:url value="/css/glisse.css?1.css" />">
    <link rel="stylesheet" href="<c:url value="/css/jquery.jgrowl.css" />">
    <link rel="stylesheet" href="<c:url value="/css/demo_table.css" />" >
    <link rel="stylesheet" href="<c:url value="/css/jquery.fancybox.css?v=2.1.4" />" media="screen" />
    
      <link rel="stylesheet" href="<c:url value="/css/icon/font-awesome.css" />">    
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-responsive.css" />">
    
    <link rel="alternate stylesheet" type="text/css" media="screen" title="green-theme" href="<c:url value="/css/color/green.css" />" />
      <link rel="alternate stylesheet" type="text/css" media="screen" title="red-theme" href="<c:url value="/css/color/red.css" />" />
      <link rel="alternate stylesheet" type="text/css" media="screen" title="blue-theme" href="<c:url value="/css/color/blue.css" />" />
    <link rel="alternate stylesheet" type="text/css" media="screen" title="orange-theme" href="<c:url value="/css/color/orange.css" />" />
    <link rel="alternate stylesheet" type="text/css" media="screen" title="purple-theme" href="<c:url value="/css/color/purple.css" />" />

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script language="JavaScript">
    Firefox = navigator.userAgent.indexOf("Firefox") >= 0;
    if(Firefox) document.write("<link rel='stylesheet' href='<c:url value="/css/moz.css" />' type='text/css'>"); 
    </script>
    
    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="<c:url value="/favicon.ico" />">
        <script src="<c:url value="/js/jquery.min.js" />"></script> 
        <script src="<c:url value="/js/jquery.dataTables.min.js" />"></script>   
  </head>
  
  <body>
    <!--BEGIN HEADER-->
    <div id="header" role="banner">
       <a id="menu-link" class="head-button-link menu-hide" href="#menu"><span>Menu</span></a>
       <!--Logo--><a href="dashboard.html" class="logo"><h1>Ringo</h1></a><!--Logo END-->
       
       <!--Search-->
       <form class="search" action="#">
         <input type="text" name="q" placeholder="Search...">
       </form>
       <!--Search END-->
       
       
       <div class="right">
       <!--message box-->
         <div class="dropdown left">
          
          <a class="dropdown-toggle head-button-link" data-toggle="dropdown" href="#"><span class="notice-new">3</span></a> 
          
          <div class="dropdown-menu pull-right messages-list">
          <div class="triangle"></div>
          
            <div class="notice-title">You Have 3 Massages</div>
            <!--message #1-->
            <div class="notice-message">
            <a href="#" class="box">
              <div class="avatar"><img src="<c:url value="/images/avatar-2.png" />" alt=""></div>
              <div class="info">
                <div class="author">Csaba Gyulai</div>
                <div class="date">Jan 9, 2013</div>
                <div class="clearfix"></div>
                <div class="text">This is Photoshop's version  of Lorem Ipsum. Proin gravida nibh vel velit.</div>
              </div>
              <div class="clearfix"></div>
            </a>
            </div>
            <!--message #2-->
            <div class="notice-message">
            <a href="#" class="box">
              <div class="avatar"><img src="<c:url value="/images/avatar-3.png" />" alt=""></div>
              <div class="info">
                <div class="author">Junkyard Sam</div>
                <div class="date">Jan 15, 2013</div>
                <div class="clearfix"></div>
                <div class="text">This is Photoshop's version  of Lorem Ipsum. Proin gravida nibh vel velit.</div>
              </div>
              <div class="clearfix"></div>
            </a>
            </div>
            <!--message #3-->
            <div class="notice-message">
            <a href="#" class="box">
              <div class="avatar"><img src="<c:url value="/images/avatar-4.png" />" alt=""></div>
              <div class="info">
                <div class="author">Lienke Raben</div>
                <div class="date">Nov 4, 2012</div>
                <div class="clearfix"></div>
                <div class="text">This is Photoshop's version  of Lorem Ipsum. Proin gravida nibh vel velit.</div>
              </div>
              <div class="clearfix"></div>
            </a>
            </div>
           
            <a href="#" class="notice-more">View All Messages</a>
          </div>
        </div>
       <!--message box end-->
       
       <!--notification box-->
         <div class="dropdown left">
          <a class="dropdown-toggle head-button-link notification" data-toggle="dropdown" href="#"><span class="notice-new">2</span></a>
          
          <div class="dropdown-menu pull-right messages-list">
          <div class="triangle"></div>
          
            <div class="notice-title">You Have 2 Notifications</div>
            <!--message #1-->
            <div class="notice-message">
            <a href="#" class="box">
              <div class="avatar"><img src="<c:url value="/images/avatar-5.png" />" alt=""></div>
              <div class="info">
                <div class="author">Jonas Anderson</div>
                <div class="date">3 hours ago</div>
                <div class="clearfix"></div>
                <div class="text">Followed <span class="follow-link">Dennis Salvatier</span></div>
              </div>
              <div class="clearfix"></div>
            </a>
            </div>
            <!--message #2-->
            <div class="notice-message">
            <a href="#" class="box">
              <div class="avatar"><img src="<c:url value="/images/avatar-6.png" />" alt=""></div>
              <div class="info">
                <div class="author">Ilias Sounas</div>
                <div class="date">19 hours ago</div>
                <div class="clearfix"></div>
                <div class="text">Commented on <span class="comment-link">Angry Cloud</span></div>
              </div>
              <div class="clearfix"></div>
            </a>
            </div>
            <!--message #3-->
            <div class="notice-message">
            <a href="#" class="box">
              <div class="avatar"><img src="<c:url value="/images/avatar-7.png" />" alt=""></div>
              <div class="info">
                <div class="author">Erin Hunting</div>
                <div class="date">19 hours ago</div>
                <div class="clearfix"></div>
                <div class="text">Liked <span class="like-link">Overnight work</span></div>
              </div>
              <div class="clearfix"></div>
            </a>
            </div>
           
            <a href="#" class="notice-more">View All Notification</a>
          </div>
        </div>
       <!--notification box end-->
       
       <!--config box-->
         <div class="dropdown left">
          <a class="dropdown-toggle head-button-link config" data-toggle="dropdown" href="#"></a>
          <div class="dropdown-menu pull-right settings-box">
          <div class="triangle-2"></div>
          
            <a href="javascript:chooseStyle('none', 30)" class="settings-link"></a>
            <a href="javascript:chooseStyle('blue-theme', 30)" class="settings-link blue"></a>
            <a href="javascript:chooseStyle('green-theme', 30)" class="settings-link green"></a>
            <a href="javascript:chooseStyle('purple-theme', 30)" class="settings-link purple"></a>
            <a href="javascript:chooseStyle('orange-theme', 30)" class="settings-link yellow"></a>
            <a href="javascript:chooseStyle('red-theme', 30)" class="settings-link red"></a>
            <div class="clearfix"></div>
          </div>
        </div>
       <!--config box end-->
       
       <!--profile box
         <div class="dropdown left profile">
          <a class="dropdown-toggle" data-toggle="dropdown" href="#">
            <span class="double-spacer"></span>
            <div class="profile-avatar"><img src="images/avatar.png" alt=""></div>
            <div class="profile-username"><span>Welcome,</span> John Doe!</div>
            <div class="profile-caret"> <span class="caret"></span></div>
            <span class="double-spacer"></span>
          </a>
          <div class="dropdown-menu pull-right profile-box">
          <div class="triangle-3"></div>
          
            <ul class="profile-navigation">
              <li><a href="#"><i class="icon-user"></i> My Profile</a></li>
              <li><a href="#"><i class="icon-cog"></i> Settings</a></li>
              <li><a href="#"><i class="icon-info-sign"></i> Help</a></li>
              <li><a href="index.html"><i class="icon-off"></i> Logout</a></li>
            </ul>
          </div>
        </div>
        <div class="clearfix"></div>
       <!profile box end-->
       
       </div>
       
      
    </div>
    <!--END HEADER-->
    
    <div id="wrap">
    
    
        <!--BEGIN SIDEBAR-->
        <div id="menu" role="navigation">
          
          <ul class="additional-menu">
             <li class="active"><a href="<c:url value="/" />"><i class="icon-home"></i> Dashboard</a></li>
             <li><a href="<c:url value="/" />admin/sla"><i class="icon-picture"></i> SLAs</a></li>
             <li><a href="icons.html"><i class="icon-star"></i> Icons</a></li>
             <li><a href="calendar.html"><i class="icon-calendar"></i> Calendar</a></li>
          </ul>
          
          <div class="clearfix"></div>
          
          
        </div>
        <!--SIDEBAR END-->
    
        
        <!--BEGIN MAIN CONTENT-->
        <div id="main" role="main">
          <div class="block">
          <div class="clearfix"></div>
  <tiles:insertAttribute name="body" />


               
               <!--BEGIN FOOTER-->
               <div class="footer">
                  <div class="left">Copyright &copy; 2013</div>
                  <div class="right"><a href="#">JV &amp; PS</a></div>
                  <div class="clearfix"></div>
               </div>
               <!--BEGIN FOOTER END-->
              
          <div class="clearfix"></div> 
          </div><!--end .block-->
        </div>
        <!--MAIN CONTENT END-->
    
    </div>
    <!--/#wrapper-->


    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/js/jquery-ui.min.js" />"></script>
   
    <script src="<c:url value="/js/bootstrap.min.js" />"></script>
    <script src="<c:url value="/js/google-code-prettify/prettify.js" />"></script>
   
    <script src="<c:url value="/js/jquery.flot.js" />"></script>
    <script src="<c:url value="/js/jquery.flot.pie.js" />"></script>
    <script src="<c:url value="/js/jquery.flot.orderBars.js" />"></script>
    <script src="<c:url value="/js/jquery.flot.resize.js" />"></script>
    <script src="<c:url value="/js/jquery.flot.categories.js" />"></script>
    <script src="<c:url value="/js/graphtable.js" />"></script>
    <script src="<c:url value="/js/fullcalendar.min.js" />"></script>
    <script src="<c:url value="/js/chosen.jquery.min.js" />"></script>
    <script src="<c:url value="/js/autoresize.jquery.min.js" />"></script>
    <script src="<c:url value="/js/jquery.autotab.js" />"></script>
    <script src="<c:url value="/js/jquery.jgrowl_minimized.js" />"></script>
    <script src="<c:url value="/js/jquery.stepy.min.js" />"></script>
    <script src="<c:url value="/js/jquery.validate.min.js" />"></script>
    <script src="<c:url value="/js/raphael.2.1.0.min.js" />"></script>
    <script src="<c:url value="/js/justgage.1.0.1.min.js" />"></script>
      <script src="<c:url value="/js/glisse.js" />"></script>
      <script src="<c:url value="/js/styleswitcher.js" />"></script>
      <script src="<c:url value="/js/moderniz.js" />"></script>
    <script src="<c:url value="/js/jquery.sparkline.min.js" />"></script>
    <script src="<c:url value="/js/slidernav-min.js" />"></script>
    <script type="text/javascript" src="<c:url value="/js/jquery.fancybox.js?v=2.1.4" />"></script>
    
    <!--settings infobox charts-->
    <script src="<c:url value="/js/float.settings.infobox.js" />"></script>


  </body>
</html>