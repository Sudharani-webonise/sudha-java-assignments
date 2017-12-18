"use strict";

$(document).ready(function(){
  var currentUrl = (window.location.href).split('?');
  var windowHeight = $(window).outerHeight();
  var wndowWidth = $(window).outerWidth();
  var IE9Browser = /MSIE\s/.test(navigator.userAgent) && parseFloat(navigator.appVersion.split("MSIE")[1]) < 10;
  var oldURL = document.referrer.split('?')[1];
  let $document = $(document);

  var newOTPLink = '/template/frontend/signup/congrats.html';
  if(oldURL === 'signup'){
    $('#otpLabel').text('Enter your OTP to complete the Signup process');
    $('#otpFinalLink').attr('href', newOTPLink);
  }
  // NOTE: Opening Dropdown on header
  var toolTipBoxHTML = '<i class="toolTipBox">Your cart is empty</i>';
  $document.on('click', '.userInfoWrap', function(e){
    e.stopPropagation();
    if($(this).hasClass('activeLink')){
      $(this).removeClass('activeLink');
      $('.settingsDropDown').slideUp();
    }else {
      $('.rightMainMenus').removeClass('activeLink');
      $('.cartMsgBox').removeClass('slideIn').stop().fadeOut('fast');
      $(this).addClass('activeLink');
      $('.settingsDropDown').slideDown();
	  $('.buyDropDown').slideUp();
    }
  });

 $document.on('click', '.buyingLink', function(e){
    e.stopPropagation();
    if($(this).hasClass('activeLink')){
      $(this).removeClass('activeLink');
      $('.buyDropDown').slideUp();
    }else {
	$('.rightMainMenus').removeClass('activeLink');
      $('.cartMsgBox').removeClass('slideIn').stop().fadeOut('fast');
      $(this).addClass('activeLink');	
      $('.buyDropDown').slideDown();
	  $('.settingsDropDown').slideUp();
    }
  });

  $(document).on('click', '.darkPinkBtn',function(){
    if($('.cartMsgBox').hasClass('slideIn')){
      $('.rightMainMenus').removeClass('activeLink');
      $('.cartMsgBox').removeClass('slideIn').stop().fadeOut('fast');
    }
  });

  $(document).on('click', '.shoppingCartLink', function(e){
    e.stopPropagation();
    if(!($(this).hasClass('activeLink'))){
      $('.rightMainMenus').removeClass('activeLink');
      $('.settingsDropDown').slideUp();
	  $('.buyDropDown').slideUp();
      $(this).find('.toolTipBox').remove();
      $(this).addClass('activeLink');
      $('.cartMsgBox').stop().fadeIn('fast');
      $('.cartMsgBox').delay(1000).addClass('slideIn');
    }else {
      $(this).removeClass('activeLink');
      $('.cartMsgBox').removeClass('slideIn');
      $('.cartMsgBox').fadeOut('fast');
      setTimeout(function(){
        $('.shoppingCartLink').append(toolTipBoxHTML);
      }, 500);
    }
  });

  $(document).on('click', '.cartMsgBox', function(e){
    e.stopPropagation();
  });

  $(document).on('click', 'html, body',function(){
    $('#userInfoLink').removeClass('activeLink');
    $('.settingsDropDown').slideUp();
	$('.buyDropDown').slideUp();
  });
  // NOTE: Window scroll adding css
  $(window).scroll(function() {
    var scroll = $(window).scrollTop();
    if (scroll >= 10) {
      if(currentUrl[1] === 'dashboard'){
        $('.headerWrap').addClass('bottomShadowScroll');
      }
      $('.tableHeader, .breadCrumbSection, .midHeader').addClass('bottomShadowScroll');
    }else {
      $('.headerWrap, .tableHeader, .breadCrumbSection, .midHeader').removeClass('bottomShadowScroll');
    }
  });

  //NOTE: For left side slide menu open
  $(document).on('click', '.hamburgerMenu', function(e){
    e.stopPropagation();
    $('.allListFilter, .filterIconWrap').removeClass('filterActive');
    $('.blackOverlay').fadeOut();
    $('.toolTipBox').text('Filter');
    $('.filterIconWrap').find('span').removeClass('NMIcon-filterClose');
    if(!$(this).hasClass('active')){
      $('.mainMenu').addClass('slideIn');
      $(this).addClass('active');
      $(this).find('.toolTipBox').text('Close');
      setTimeout(function() {
        $('.hamburgerMenu').addClass('activeCross');
      }, 500);
    }else {
      $('.mainMenu').removeClass('slideIn');
      $(this).find('.toolTipBox').text('Side Menu');
      $(this).removeClass('activeCross');
      setTimeout(function() {
        $('.hamburgerMenu').removeClass('active');
      }, 500);
    }
  });

  //NOTE: Toggle Radio button
  $('.customRadioLabel').on('click', function(e){
    e.preventDefault();
    var currentRadioVal =  $(this).find('input').attr('id');
    if(currentRadioVal === 'individual') {
      $('.companyField').hide();
      $('.companyPanNum').addClass('hide');
      $('.indiviualPanNum').removeClass('hide');
      $('.addressFieldWrap').addClass('marginTop20');
    }else {
      $('.companyField').show();
      $('.indiviualPanNum').addClass('hide');
      $('.companyPanNum').removeClass('hide');
      $('.addressFieldWrap').removeClass('marginTop20');
    }
    $(this).closest('.radioButtons').toggleClass('toggleSlide');
  });

  //NOTE: Custom selectBox
  $('.customSelectGroup').each(function(){
    var selectContentHeight = $(this).outerHeight();
    var firstChildText;
    $(this).find('.customSelectList').css('top', selectContentHeight);
    if($(this).find('.customSelectList > li:first-child > span').hasClass('selectChild')) {
      firstChildText = $(this).find('.customSelectList > li:first-child > .selectChild').text();
    } else {
      firstChildText = $(this).find('.customSelectList > li:first-child').text();
    }
    $(this).find('.selectContent').text(firstChildText);
  });
  $(document).on('click', '.customSelectGroup', function(e){
    e.stopPropagation();
    if(!$(this).find('.selectContent').hasClass('selectOpen')){
      $('.customSelectGroup').removeClass('dropDownOpened');
      $(this).addClass('dropDownOpened');
      $('.selectContent').removeClass('selectOpen');
      $('.customSelectList').stop().slideUp();
      $(this).find('.selectContent').addClass('selectOpen');
      $(this).find('.customSelectList').stop().slideDown();
    }else {
      $(this).removeClass('dropDownOpened');
      $(this).find('.selectContent').removeClass('selectOpen');
      $(this).find('.customSelectList').stop().slideUp();
    }
  });
  $(document).on('click', '.customSelectList > li', function (event) {
    event.stopPropagation();
    var currentText;
    $('.customSelectGroup').removeClass('dropDownOpened');
    var target = $(this);
    if(target.find('span').hasClass('selectChild')) {
      currentText = target.find('.selectChild').text();
    } else {
      currentText = target.text();
      target.parent().attr('data-selected-item', target.attr('data-id') || '');
    }
    var selectContent = target.closest('.customSelectList').siblings('.selectContent');
    if (target.find('img').length) {
      selectContent.empty();
      selectContent.append($(this).html());
      selectContent.find('span').prop('contentEditable', true);
    } else {
      selectContent.text(currentText);
    }
    selectContent.removeClass('selectOpen');
    target.closest('.customSelectList').stop().slideUp();
  });
  //NOTE: Raise Ticket Custom Checkbox
  if(IE9Browser){
    $('.otherBrowserSupport').addClass('hide');
    $('.IE9Support').removeClass('hide');
  }
  $('.customCheckBox input').on('click', function(){
    $(this).closest('.contactListInner').toggleClass('checkedContact');
    $(this).closest('.IE9Support').toggleClass('NMIcon-darkTickMark');
  })

  //NOTE: TicketListing Filter Icon Click event
  $('.filterIconWrap').on('click', function(e){
    e.stopPropagation();
    if($('.mainMenu').hasClass('slideIn')){
      $('.mainMenu').removeClass('slideIn');
      $('.mainMenu').find('.toolTipBox').text('Side Menu');
      $('.mainMenu').removeClass('activeCross');
      setTimeout(function() {
        $('.hamburgerMenu').removeClass('active');
      }, 500);
    }
    $(this).toggleClass('filterActive');
    $(this).find('span').toggleClass('NMIcon-filterClose');
    $('.allListFilter').toggleClass('filterActive');
    $('.blackOverlay').fadeToggle();
    if($(this).find('.toolTipBox').text() === 'Filter'){
      $(this).find('.toolTipBox').text('Close');
    }else {
      $(this).find('.toolTipBox').text('Filter');
    }
  });

  //NOTE: Custom Accrdion JS
  $('.accordionParent').on('click', function(e){
    e.stopPropagation();
    if(!$(this).hasClass('openAccordion')){
      $('.accordionParent').removeClass('openAccordion');
      $('.accordionChild').slideUp('slow');
      $(this).addClass('openAccordion');
      $(this).siblings('.accordionChild').slideDown('slow');
    }else{
      $(this).removeClass('openAccordion');
      $(this).siblings('.accordionChild').slideUp('slow');
    }
  });

  $('body, html').on('click', function(e){
    $('.accordionChild').slideUp('slow');
    $('.accordionParent').removeClass('openAccordion');
    $('.hamburgerMenu').removeClass('active activeCross');
    $('.hamburgerMenu').find('.toolTipBox').text('Side Menu');
    $('.mainMenu').removeClass('slideIn');
    $('.allListFilter, .filterIconWrap').removeClass('filterActive');
    $('.filterIconWrap').find('span').removeClass('NMIcon-filterClose');
    $('.blackOverlay').fadeOut();
    $('.filterIconWrap').find('.toolTipBox').text('Filter');
    $('.cartMsgBox').removeClass('slideIn').stop().fadeOut('fast');
    $('.rightMainMenus').removeClass('activeLink');
    setTimeout(function(){
      $('.shoppingCartLink').append(toolTipBoxHTML);
    }, 500);
  });

  //NOTE: Custom Checkbox Checked/Uncheked
  $('.customCheckbox').on('click', function(e){
    e.stopPropagation();
    $(this).siblings('.checkboxLayer').toggleClass('checkboxChecked');
    if($(this).is(':checked')){
      $(this).parents('.accordionParent').siblings('.accordionChild').find('input').attr('checked',true);
      $(this).parents('.accordionParent').siblings('.accordionChild').find('.checkboxLayer').addClass('checkboxChecked');
    }else{
      $(this).parents('.accordionParent').siblings('.accordionChild').find('input').attr('checked',false);
      $(this).parents('.accordionParent').siblings('.accordionChild').find('.checkboxLayer').removeClass('checkboxChecked');
    }
  });

  //NOTE: Listing heading dropDown
  $(document).on('click', '.dropDownWrapper', function(e){
    if(!($(this).hasClass('openDropDown'))){
      $('.dropDownWrapper').removeClass('openDropDown');
      $('.dataCellDropDown').slideUp();
      $(this).addClass('openDropDown');
      $(this).find('.dataCellDropDown').stop().slideDown();
    }else {
      $(this).removeClass('openDropDown');
      $(this).find('.dataCellDropDown').stop().slideUp();
    }
  });
  $(document).on('click', '.dataCellDropDown li', function(e){
    $('.dropDownWrapper').removeClass('openDropDown');
    $('.dataCellDropDown').slideUp();
  });


  $(document).mouseup(function (e) {
      var customSelectArea = $(".customSelectGroup");
      var dropDownWrapperArea = $(".dropDownWrapper");
      if (!customSelectArea.is(e.target) && customSelectArea.has(e.target).length === 0) {
          $('.selectContent').removeClass('selectOpen');
          $('.customSelectList').stop().slideUp();
          $('.customSelectGroup').removeClass('dropDownOpened');
      }
      if (!dropDownWrapperArea.is(e.target) && dropDownWrapperArea.has(e.target).length === 0) {
          $('.dropDownWrapper').removeClass('openDropDown');
          $('.dataCellDropDown').slideUp();
      }
  });

  //NOTE: Applying Height To Listing Filter
  (function filterDynamicHeight(){
    var topHeaderHeight = $('.headerWrap').outerHeight();
    var breadCrumbHeight = $('.breadCrumbSection').outerHeight();
    var listingHeadHeight = $('.tableHeader').outerHeight();
    var filterMainHeight = windowHeight - (topHeaderHeight+breadCrumbHeight+listingHeadHeight);
    $('.listingFilterWrap').css('height',filterMainHeight);
  })();

  $('.listingFilterWrap').on('click', function(e){
    e.stopPropagation();
  });

  //NOTE: Ticket Details hide second row details
  // $(document).on('click','.toggleUpDown', function(){
  //   if($(this).find('.toolTipBox').text() === 'Collapse'){
  //     $(this).find('.toolTipBox').text('Expand');
  //   }else {
  //     $(this).find('.toolTipBox').text('Collapse');
  //   }
  //   $(this).toggleClass('rotateArrow');
  //   $('.optionalView').slideToggle();
  // });

  //NOTE: Outstanding header radio click events
  $('.radioButton').on('click', function(){
    $('.radioButtonWrap').removeClass('checkedRadio selectedPay');
    $(this).closest('.radioButtonWrap').addClass('checkedRadio selectedPay');
  });

  //NOTE: Accordion on arrow click on outstanding page
  $('.accordTrigger').on('click', function(){
    $(this).parents('.tdsRow').toggleClass('tdsCertificate');
    $(this).toggleClass('active closeAccord');
    $(this).closest('.cellInWrap').siblings('.accordCell').slideToggle();
    if($(this).closest('.cellInWrap').find('.toolTipBox').text() === 'Expand')
      $(this).closest('.cellInWrap').find('.toolTipBox').text('Collapse');
    else
      $(this).closest('.cellInWrap').find('.toolTipBox').text('Expand');
  });

  //NOTE: People management
  $('.tabPeople').on('click', function(event){
    event.preventDefault();
    var currentTab = $(this).attr('href');
    $('.tabPeople').removeClass('activeTab');
    $(this).addClass('activeTab');
    $('.settingTabWrap').removeClass('activeContentTab');
    $(currentTab).addClass('activeContentTab');
  });


  //NOTE: Oustanding Row color toggle
  $('.cellInWrap .cssCheckBox > input[type="checkbox"], .tabelDataCell .cssCheckBox >  input[type="checkbox"]').on('change', function(e){
    e.stopPropagation();
    if($(this).is(":checked")){
      $(this).closest('.tabelDataCell').addClass('checkedRow');
    }else{
      $(this).closest('.tabelDataCell').removeClass('checkedRow');
    }
  });
  $('.accordRow .cssCheckBox >  input[type="checkbox"]').on('change', function(e){
    e.stopPropagation();
    if($(this).is(":checked")){
      $(this).closest('.tabelDataCell').removeClass('checkedRow');
      $(this).closest('.accordRow').addClass('checkedRow');
    }else{
      $(this).closest('.accordRow').removeClass('checkedRow');
    }
  });

  //NOTE: people list button click action stoppropagation
  $('.buttonControlWrap a').on('click', function(e){
    e.stopPropagation();
  });

  //NOTE: Sorting arrow rotate css
  $document.on('click', '.sortingCell', function(event) {
    $(this).toggleClass('sortArrow');
  });

  //NOTE: Google material Ripple effect js
  var ink, d, x, y;
  $('.rippleBtn, .reOpenBtn, .provideInputBtn').on('click', function(e){
    e.stopPropagation();

    if($(this).find('.rippleInk').length === 0) {
        $(this).prepend('<span class="rippleInk"></span>');
    }

    ink = $(this).find('.rippleInk');
    ink.removeClass('animateRipple');

    if(!ink.height() && !ink.width()){
        d = Math.max($(this).outerWidth(), $(this).outerHeight());
        ink.css({height: d, width: d});
    }

    x = e.pageX - $(this).offset().left - ink.width()/2;
    y = e.pageY - $(this).offset().top - ink.height()/2;

    ink.css({top: y+'px', left: x+'px'}).addClass('animateRipple');
  });

  // Note: For Business Change request PopUp
  $('.changeRequestBtn').on('click', function(){
    $('.changeRequestPopUp').fadeIn(300)
    $('.fullscreenOverlay').fadeIn(500)
  });

  $('.changeRequestPopUp .closePop').on('click', function(){
    $('.changeRequestPopUp ').fadeOut(300);
    $('.fullscreenOverlay').fadeOut(500)
  });

  $('.sendRequestBtn').on('click', function(){
    $('.requestMailSentPopUp').css('visibility','visible');
    $('.changeRequestPopUp').fadeOut(300)
    $('.fullscreenOverlay').fadeIn(500)
  });

  $('.requestMailSentPopUp .okBtn').on('click', function(){
    $('.requestMailSentPopUp ').css('visibility','hidden');
    $('.fullscreenOverlay').fadeOut(500)
  });

  //Note: For outstanding download file PopUp
  $document.on('click', '.invoiceNoCell',function(){
    $('.ticketIdPopUp').fadeIn(300)
  });

  $('.ticketIdPopUp a').on('click', function(){
    $('.ticketIdPopUp').fadeOut(300);
    $('.fullscreenOverlay').fadeOut(500)
  });

  $('.confirmTickBtn').on('click', function(e){
    $(this).hide();
    $(this).siblings('.confirmText').show();
  });

  //NOTE: Service ledger category tool-tip
  $('#serviceCategoryToolTip').hide();
  $('.serviceCategorySelect').on('mouseenter', function() {
    var leftCord = $(this).offset().left;
    $('#serviceCategoryToolTip').css('left', leftCord);
    $('#serviceCategoryToolTip').show();
  }).mouseout(function() {
    $('#serviceCategoryToolTip').hide();
  });

  // Note: For Service ledger tabs
  $('.ledgerTabs').hide();
  $('.ledgerTabs:first').show();

  $('.tabInnerContent').on('click' , function (e) {
      $('.leftTabs li').removeClass('active');
      $(this).closest('li').addClass('active');
  });

  $('.slideSearchLeft').click(function() {
    $( ".sliderWrapper" ).hide()
    $( ".slideLeft" ).animate({
      width: "100%"
  }, 1500 );
  });


  $('.slideSearchRight').click(function() {
    $( ".slideLeft" ).animate({
      width: "0"
    }, 1500, function(){
      $(".sliderWrapper").show();
    } );
  });
});
