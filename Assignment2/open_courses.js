let edit_obj;
let roomarrangement=[];


function setDate(){
    var date_now = new Date();
    var year = date_now.getFullYear();
    var month = date_now.getMonth()+1 < 10 ? "0"+(date_now.getMonth()+1) : (date_now.getMonth()+1);
    var date = date_now.getDate() < 10 ? "0"+date_now.getDate() : date_now.getDate();
    document.getElementById('course-date').setAttribute('min',year+"-"+month+"-"+date);
}

function onclickAddCourse(){
    setDate();
    document.forms[0].reset();
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].setAttribute('disabled','true');
    }
    document.getElementById('add_course').style.display='block';
    document.getElementById('model1').style.display='block';
    document.getElementById('add_course_button').style.display='none';
}

function closedialog(){
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].removeAttribute('disabled');
    }
    document.getElementById('add_course').style.display='none';
    document.getElementById('add_course_button').style.display='block';
    document.getElementById('model1').style.display='none';
}

function checkSubmit(){
    let courseName=document.forms[0]['course-name'].value;
    let coursecode=document.forms[0]['course-code'].value;
    let lang=document.forms[0].language.value;
    let teacher=document.forms[0].teacher.value;
    let coursedate=document.forms[0]['course-date'].value;
    let coursetime=document.forms[0]['course-time'].value;
    let courseLocation= document.getElementById('course-location').value;
    let duration=document.forms[0]['duration'].value;
    //alert(courseName+" "+coursecode+" "+lang+" "+teacher+" "+coursedate+" "+coursetime+" "+courseLocation+" "+duration);
    let onlyeng=new RegExp(/^[a-zA-Z]+$/)
    let engspace = new RegExp(/^[A-Za-z ]+$/)
    let onlynum= new RegExp(/^[0-9]+$/)
    let onlyengandnum=new RegExp(/^[a-zA-Z0-9]+$/)
    let onlyspace = new RegExp(/^[ ]+$/)

    if (courseName===""||coursecode===""||lang===""||teacher===""||coursedate===""||coursetime===""||courseLocation==="null"||duration===""){
        alert("all attributes must be non-null!")
        return false;
    }
    if (!engspace.test(courseName)||onlyspace.test(courseName)){
        alert("invalid course name!");
        return false;
    }
    if (!onlyengandnum.test(coursecode)||onlyeng.test(coursecode)||onlynum.test(coursecode)){
        alert("invalid course code");
        return false;
    }
    if (!onlyeng.test(teacher)){
        alert("invalid teacher name!");
        return false;
    }
    if (!onlynum.test(duration)){
        alert("invalid duration!");
        return false;
    }
    if (!checkroomConflict(courseLocation,coursetime,duration,coursedate)){
        alert("room conflict!")
        return false;
    }
    if (!checkCourseDuplication(courseName,coursecode)){
        alert("two same course code cannot have different course name!")
        return false;
    }
    if (!checkTeacherOneLecPerDay(teacher,coursedate)){
        alert("one teacher can only have one lec per day")
        return false;
    }
    if (!checkCoursePerDay(coursecode,coursedate)){
        alert("One course is scheduled at most once a day")
        return false;
    }

    else onclickSubmitCourse();
}

function checkroomConflict(courseLocation, coursetime, duration, coursedate){
    //no two lecture share the same room at the same time
    let flag=true;
    let roomidx;
    if (courseLocation==="Teaching Building No.1 Lecture Hall"){
        roomidx=0;
    }
    else if (courseLocation==="Research Building Lecture Hall"){
        roomidx=1;
    }
    else {
        roomidx=2;
    }
    let sthour= coursetime.split(":")[0];
    let stmin= coursetime.split(":")[1];
    var sthour_int=parseInt(sthour);
    var stmin_int=parseInt(stmin);
    var dur_int = parseInt(duration);
    let endhour_int=Math.floor((sthour_int*60+stmin_int+dur_int)/60);
    let endmin_int=(sthour_int*60+stmin_int+dur_int)-endhour_int*60;
    //alert(endhour_int);
    //alert(endmin_int);
    for (let i=0;i<roomarrangement.length;i++) {
        if (roomarrangement.at(i).roomid===roomidx && roomarrangement.at(i).coursedate===coursedate){
            if ((roomarrangement.at(i).endh>sthour_int || (roomarrangement.at(i).endh===sthour_int&& roomarrangement.at(i).endm>stmin_int)) &&
                (roomarrangement.at(i).sth<endhour_int||(roomarrangement.at(i).sth===sthour_int && roomarrangement.at(i).stm<endmin_int))
            )
                flag=false;
                //alert(flag);
        }
    }
    return flag;
}

function checkCourseDuplication(coursename, coursecode){
    //two same course code cannot have different course name, but the reverse is valid
    for (let i=0;i<roomarrangement.length;i++){
        if (roomarrangement.at(i).coursecod===coursecode && roomarrangement.at(i).coursename!==coursename)
            return false;
    }
    return true;
}

function checkTeacherOneLecPerDay(teacher,coursedate){
    //one teacher can only have one lec per day
    for (let i=0;i<roomarrangement.length;i++){
        if (roomarrangement.at(i).coursedate===coursedate && roomarrangement.at(i).teacher===teacher){
            return false;
        }
    }
    return true;
}

function checkCoursePerDay(coursecode,coursedate){
    //One course is scheduled at most once a day.
    for (let i=0;i<roomarrangement.length;i++){
        if (roomarrangement.at(i).coursedate===coursedate && roomarrangement.at(i).coursecod===coursecode){
            return false;
        }
    }
    return true;
}

function onclickSubmitCourse(){
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].removeAttribute('disabled');
    }
    let bodyObj = document.getElementById("tbody");
    if (!bodyObj) {
        alert("Body of Table not Exist!");
        return;
    }
    let courseLocation= document.getElementById('course-location').value;
    let rowCount = bodyObj.rows.length;
    let cellCount = bodyObj.rows[0].cells.length;
    bodyObj.style.display = ""; // display the tbody
    let newRow = bodyObj.insertRow(rowCount++);
    newRow.insertCell(0).innerHTML = document.forms[0]["course-name"].value;
    newRow.insertCell(1).innerHTML = document.forms[0]["course-code"].value;
    newRow.insertCell(2).innerHTML = document.forms[0].language.value;
    newRow.insertCell(3).innerHTML = document.forms[0].teacher.value;
    newRow.insertCell(4).innerHTML = document.forms[0]["course-date"].value;
    newRow.insertCell(5).innerHTML = document.forms[0]["course-time"].value;
    newRow.insertCell(6).innerHTML = courseLocation;
    newRow.insertCell(7).innerHTML = document.forms[0]["duration"].value+"min";
    newRow.insertCell(8).innerHTML = bodyObj.rows[0].cells[cellCount - 1].innerHTML; // copy the "delete" button
    bodyObj.rows[0].style.display = "none"; // hide first row

    let roomidx;
    let coursecode=document.forms[0]["course-code"].value;
    let coursenam = document.forms[0]["course-name"].value;
    let teach= document.forms[0].teacher.value;
    if (courseLocation==="Teaching Building No.1 Lecture Hall"){
        roomidx=0;
    }
    else if (courseLocation==="Research Building Lecture Hall"){
        roomidx=1;
    }
    else {
        roomidx=2;
    }
    let starttime=document.forms[0]["course-time"].value;
    let sthour= starttime.split(":")[0];
    let stmin= starttime.split(":")[1];
    let durat = document.forms[0]["duration"].value;
    var sthour_int=parseInt(sthour);
    var stmin_int=parseInt(stmin);
    //alert(sthour_int)
    var dur_int = parseInt(durat);
    let endhour_int=Math.floor((sthour_int*60+stmin_int+dur_int)/60);
    let endmin_int=(sthour_int*60+stmin_int+dur_int)-endhour_int*60;
    let courseda= document.forms[0]["course-date"].value;
    //TODO:
    //add to list
    roomarrangement.push({coursename:coursenam, coursecod:coursecode,teacher:teach,roomid:roomidx,coursedate:courseda, sth: sthour_int, stm: stmin_int, endh: endhour_int, endm: endmin_int});


    closedialog();
    document.getElementById('add_course_button').style.display='block';
    document.getElementById('model1').style.display='none';
}

function removeRow(inputobj){
    if (!inputobj) return;
    let parentTD = inputobj.parentNode;
    let parentTR = parentTD.parentNode;
    let parentTBODY = parentTR.parentNode;
    parentTBODY.removeChild(parentTR);

    //remove from list

    roomarrangement=[];
    //alert(parentTBODY.rows.length)
    for (let i=1;i<parentTBODY.rows.length;i++){
        let roomidx;
        let courseLocation=parentTBODY.rows[i].cells[6].innerHTML;
        if (courseLocation==="Teaching Building No.1 Lecture Hall"){
            roomidx=0;
        }
        else if (courseLocation==="Research Building Lecture Hall"){
            roomidx=1;
        }
        else {
            roomidx=2;
        }
        let starttime=parentTBODY.rows[i].cells[5].innerHTML;
        let sthour= starttime.split(":")[0];
        let stmin= starttime.split(":")[1];
        let durat = parentTBODY.rows[i].cells[7].innerHTML.replace("min","");
        var sthour_int=parseInt(sthour);
        var stmin_int=parseInt(stmin);
        //alert(sthour_int)
        var dur_int = parseInt(durat);
        let endhour_int=Math.floor((sthour_int*60+stmin_int+dur_int)/60);
        let endmin_int=(sthour_int*60+stmin_int+dur_int)-endhour_int*60;
        let courseda= parentTBODY.rows[i].cells[4].innerHTML;
        roomarrangement.push({coursename:parentTBODY.rows[i].cells[0].innerHTML, coursecod:parentTBODY.rows[i].cells[1].innerHTML, teacher:parentTBODY.rows[i].cells[3].innerHTML,roomid:roomidx,coursedate:courseda, sth: sthour_int, stm: stmin_int, endh: endhour_int, endm: endmin_int})
    }
    //alert(roomarrangement.length);
}

function edit(inputobj){
    document.getElementById('add_course_button').style.display='none';
    let editbuttonlist=document.getElementsByName('EditButton');
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].setAttribute('disabled','true');
    }
    let delbuttonlist = document.getElementsByName('deleteButton');
    for (let i=0;i<delbuttonlist.length;i++){
        delbuttonlist[i].setAttribute('disabled','true');
    }
    if (!inputobj) return;
    edit_obj=inputobj;
    let parentTD = inputobj.parentNode;
    let parentTR = parentTD.parentNode;

    document.getElementById('edit-course-name').value=parentTR.cells[0].innerHTML;
    document.getElementById('edit-course-code').value=parentTR.cells[1].innerHTML;
    let language=parentTR.cells[2].innerHTML;
    //alert(language)
    document.getElementById(language).setAttribute('checked','true');
    document.getElementById('edit-teacher').value=parentTR.cells[3].innerHTML;
    document.getElementById('edit-course-date').value=parentTR.cells[4].innerHTML;
    document.getElementById('edit-course-time').value=parentTR.cells[5].innerHTML;
    document.getElementById('edit-course-location').value=parentTR.cells[6].innerHTML;
    document.getElementById('edit-duration').value=parentTR.cells[7].innerHTML.replace("min","");
    //document.getElementsById(language).checked = true;

    document.getElementById('edit_course').style.display='block';
    document.getElementById('model1').style.display='block';
}

function submitEdit(){
    let courseName= document.getElementById('edit-course-name').value;
    let courseCode= document.getElementById('edit-course-code').value;
    let list_options= document.getElementsByName('edit-language')
    let language;
    for (let i=0;i<list_options.length;i++){
        if (list_options[i].checked)
            language=list_options[i].value;
    }

    let teacher=document.getElementById('edit-teacher').value;
    let coursedate= document.getElementById('edit-course-date').value;
    let coursetime= document.getElementById('edit-course-time').value;
    let courselocation = document.getElementById('edit-course-location').value;
    let duration= document.getElementById('edit-duration').value;
    let parentTD = edit_obj.parentNode;
    let parentTR = parentTD.parentNode;
    parentTR.cells[0].innerHTML=courseName;
    parentTR.cells[1].innerHTML=courseCode;
    parentTR.cells[2].innerHTML=language;
    parentTR.cells[3].innerHTML=teacher;
    parentTR.cells[4].innerHTML=coursedate;
    parentTR.cells[5].innerHTML=coursetime;
    parentTR.cells[6].innerHTML=courselocation;
    parentTR.cells[7].innerHTML=duration+"min";
    closeeditdialog();
    document.getElementById('add_course_button').style.display='block';
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].removeAttribute('disabled');
    }
    let delbuttonlist = document.getElementsByName('deleteButton');
    for (let i=0;i<delbuttonlist.length;i++){
        delbuttonlist[i].removeAttribute('disabled');
    }
    document.getElementById('model1').style.display='none';
}

function closeeditdialog(){
    document.getElementById('edit_course').style.display='none';
    document.getElementById('model1').style.display='none';
    document.getElementById('add_course_button').style.display='block';
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].removeAttribute('disabled');
    }
    let delbuttonlist = document.getElementsByName('deleteButton');
    for (let i=0;i<delbuttonlist.length;i++){
        delbuttonlist[i].removeAttribute('disabled');
    }
}