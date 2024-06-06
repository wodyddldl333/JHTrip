document.getElementById('domain').addEventListener('change', function() {
  var domainInput = document.getElementById('email');
  var domainSelect = document.getElementById('domain');
  if (domainSelect.value === '직접입력') {
    domainInput.value = ''; // 입력 필드 초기화
    domainInput.disabled = false; // 입력 필드 활성화
    domainInput.focus(); // 입력 필드에 포커스
  } else {
    domainInput.value = domainSelect.value;
    domainInput.disabled = true; // 입력 필드 비활성화
  }
});