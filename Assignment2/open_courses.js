let edit_obj;

function onclickAddCourse(){
    document.forms[0].reset();
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].setAttribute('disabled','true');
    }
    document.getElementById('add_course').style.display='block';
    document.getElementById('add_course_button').style.display='none';
}

function closedialog(){
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].removeAttribute('disabled');
    }
    document.getElementById('add_course').style.display='none';
    document.getElementById('add_course_button').style.display='block';
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
    newRow.insertCell(7).innerHTML = document.forms[0]["duration"].value;
    newRow.insertCell(8).innerHTML = bodyObj.rows[0].cells[cellCount - 1].innerHTML; // copy the "delete" button
    bodyObj.rows[0].style.display = "none"; // hide first row
    closedialog();
    document.getElementById('add_course_button').style.display='block';
}

function removeRow(inputobj){
    if (!inputobj) return;
    let parentTD = inputobj.parentNode;
    let parentTR = parentTD.parentNode;
    let parentTBODY = parentTR.parentNode;
    parentTBODY.removeChild(parentTR);
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
    document.getElementById('edit-duration').value=parentTR.cells[7].innerHTML;
    //document.getElementsById(language).checked = true;

    document.getElementById('edit_course').style.display='block';
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
    parentTR.cells[7].innerHTML=duration;
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
}

function closeeditdialog(){
    document.getElementById('edit_course').style.display='none';
    let editbuttonlist=document.getElementsByName('EditButton')
    for (let i=0;i<editbuttonlist.length;i++){
        editbuttonlist[i].removeAttribute('disabled');
    }
    let delbuttonlist = document.getElementsByName('deleteButton');
    for (let i=0;i<delbuttonlist.length;i++){
        delbuttonlist[i].removeAttribute('disabled');
    }
}